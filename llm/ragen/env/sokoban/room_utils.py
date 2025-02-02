
import random
import numpy as np
import marshal
from typing import *


TYPE_LOOKUP = {
    0: 'wall',
    1: 'empty space',
    2: 'box target',
    3: 'box on target',
    4: 'box not on target',
    5: 'player'
}

ACTION_LOOKUP = {
    0: 'push up',
    1: 'push down',
    2: 'push left',
    3: 'push right',
    4: 'move up',
    5: 'move down',
    6: 'move left',
    7: 'move right',
}

# Moves are mapped to coordinate changes as follows
# 0: Move up
# 1: Move down
# 2: Move left
# 3: Move right
CHANGE_COORDINATES = {
    0: (-1, 0),
    1: (1, 0),
    2: (0, -1),
    3: (0, 1)
}

# Possible empty space combinations during the walk
MASKS = [
    [
        [0, 0, 0],
        [1, 1, 1],
        [0, 0, 0]
    ],
    [
        [0, 1, 0],
        [0, 1, 0],
        [0, 1, 0]
    ],
    [
        [0, 0, 0],
        [1, 1, 0],
        [0, 1, 0]
    ],
    [
        [0, 0, 0],
        [1, 1, 0],
        [1, 1, 0]
    ],
    [
        [0, 0, 0],
        [0, 1, 1],
        [0, 1, 0]
    ]
]

# Possible directions during the walk
DIRECTIONS = [(1, 0), (0, 1), (-1, 0), (0, -1)]


class RoomTopologyGenerator(object):
    """Generate a room topology, which consists of empty floors and walls.
    1 -> empty floor
    0 -> walls
    """
    def __init__(self, seed: int = None):
        self.rng = random.Random(seed)

    def choose_direction(self):
        return self.rng.choice(DIRECTIONS)

    def choose_mask(self):
        return self.rng.choice(MASKS)

    def generate(self, dim: Tuple[int, int] = (13, 13), p_change_directions: float = 0.35, num_steps: int = 15) -> np.ndarray:
        dim_x, dim_y = dim
        room = np.zeros(dim, dtype=int)
        # Starting position of random walk
        x, y = self.rng.randint(1, dim_x - 1), self.rng.randint(1, dim_y - 1)
        direction = self.choose_direction()
        for s in range(num_steps):
            # Change direction randomly
            if self.rng.random() < p_change_directions:
                direction = self.choose_direction()

            # Update position
            x += direction[0]
            y += direction[1]
            x = max(min(x, dim_x - 2), 1)
            y = max(min(y, dim_y - 2), 1)

            # Apply mask
            mask = self.choose_mask()
            mask_start_x = x - 1
            mask_start_y = y - 1
            room[mask_start_x: mask_start_x + 3, mask_start_y: mask_start_y + 3] += mask

        room[room > 0] = 1
        room[:, [0, dim_y - 1]] = 0
        room[[0, dim_x - 1], :] = 0
        return room


def place_boxes_and_player(room: np.ndarray, num_boxes: int) -> np.ndarray:
    """Places the player and the boxes into the floors in a room."""
    # Get all available positions
    possible_positions: np.ndarray = np.argwhere(room == 1)
    num_possible_positions = len(possible_positions)
    if num_possible_positions <= num_boxes + 1:   # 1 for player
        raise RuntimeError(f'Not enough free spots (#{num_possible_positions}) to '
                           f'place 1 player and {num_boxes} boxes.')

    # Place players(type=5) and boxes(type=2, targets)
    positions = random.sample(possible_positions, num_boxes + 1)
    room[positions[0]] = 5
    for position in positions[1:]:
        room[position] = 2  # box target
    return room


# Global variables used for reverse playing.
explored_states = set()
num_boxes = 0
best_room_score = -1
best_room = None
best_box_mapping = None
best_action_sequence = []


def reverse_playing(room_state: np.ndarray, room_structure: np.ndarray, search_depth: int = 100) -> Tuple[np.ndarray, Dict, List]:
    """
    This function plays Sokoban reverse in a way, such that the player can
    move and pull boxes.
    It ensures a solvable level with all boxes not being placed on a box target.
    """
    global explored_states, num_boxes, best_room_score, best_room, best_box_mapping, best_action_sequence

    # Box_Mapping is used to calculate the box displacement for every box
    box_locations = np.argwhere(room_state == 2)
    num_boxes = len(box_locations[0])
    box_mapping = {tuple(loc): tuple(loc) for loc in box_locations}

    # explored_states globally stores the best room state and score found during search
    explored_states = set()
    best_room_score = -1
    best_room: np.ndarray = None
    best_box_mapping = box_mapping
    best_action_sequence = []

    dfs(room_state, room_structure, box_mapping, box_swaps=0, last_pull=(-1, -1), ttl=search_depth, action_sequence=[])
    return best_room, best_box_mapping, best_action_sequence


def dfs(room_state: np.ndarray, room_structure: np.ndarray, box_mapping: Dict[Tuple[int, int], Tuple[int, int]],
        box_swaps: int = 0, last_pull: Tuple[int, int] = (-1, -1), ttl: int = 300, action_sequence: List = None) -> None:
    """Searches through all possible states of the room.
    This is a recursive function, which stops if the ttl is reduced to 0 or over 1.000.000 states have been explored."""
    global explored_states, num_boxes, best_room_score, best_room, best_box_mapping, best_action_sequence
    ttl -= 1
    if ttl <= 0 or len(explored_states) >= 300000:
        return
    state_to_hash = marshal.dumps(room_state)

    if state_to_hash in explored_states:
        return

    explored_states.add(state_to_hash)
    # Add current state and its score to explored states
    room_score = 0
    if np.sum(room_state == 2) == num_boxes:
        room_score = box_swaps * box_displacement_score(box_mapping)
    if room_score > best_room_score:
        best_room = room_state.copy()
        best_room_score = room_score
        best_box_mapping = box_mapping.copy()
        best_action_sequence = action_sequence.copy()

    for action in ACTION_LOOKUP:
        # The state and box mapping need to be copied to ensure
        # every action starts from a similar state.

        # TODO: A tentitive try here to make less moves
        if action >= 4:
            continue
        next_room_state = room_state.copy()
        next_box_mapping = box_mapping.copy()
        next_room_state, next_box_mapping, next_last_pull \
            = reverse_move(next_room_state, room_structure, next_box_mapping, last_pull, action)
        next_box_swaps = box_swaps
        if next_last_pull != last_pull:
            next_box_swaps += 1
        next_action_sequence = action_sequence + [action]
        dfs(next_room_state, room_structure, next_box_mapping, next_box_swaps, next_last_pull, ttl, next_action_sequence)


def reverse_move(room_state: np.ndarray, room_structure: np.ndarray, box_mapping: Dict[Tuple[int, int], Tuple[int, int]], last_pull: Tuple[int, int], action: int):
    """Perform reverse action"""
    player_x, player_y = np.argwhere(room_state == 5)[0]
    change_x, change_y = CHANGE_COORDINATES[action % 4]
    next_position_x, next_position_y = player_x + change_x, player_y + change_y
    # Check if next position is an empty floor or an empty box target
    if room_state[next_position_x, next_position_y] in [1, 2]:
        # Move player, independent of pull or move action
        room_state[player_x, player_y] = room_structure[player_x, player_y]
        room_state[next_position_x, next_position_y] = 5
        # In addition, try to pull a box if the action is a pull action
        if action < 4:
            box_location_x = player_x - change_x
            box_location_y = player_y - change_y
            if room_state[box_location_x, box_location_y] in [3, 4]:  # 表示原来的位置确实是箱子
                # Perform pull of the adjacent box
                # 箱子对应被拉到的位置就是玩家旧位置
                room_state[player_x, player_y] = 3 if room_structure[player_x, player_y] == 2 else 4
                room_state[box_location_x, box_location_y] \
                    = room_structure[box_location_x, box_location_y]
                # Update the box mapping
                for k, v in box_mapping.items():
                    if v == (box_location_x, box_location_y):
                        box_mapping[k] = (player_x, player_y)
                        last_pull = k
                        break
    return room_state, box_mapping, last_pull


def box_displacement_score(box_mapping: Dict[Tuple[int, int], Tuple[int, int]]) -> int:
    """Calculates the sum of all Manhattan distances, between the boxes and their origin box targets."""
    score = 0
    for (target_x, target_y), (location_x, location_y) in box_mapping.items():
        score += abs(location_x - target_x)
        score += abs(location_y - target_y)
    return score


def generate_room(dim: Tuple[int, int] = (13, 13), p_change_directions: float = 0.35, num_steps: int = 25,
                  num_boxes: int = 3, tries: int = 4, search_depth: int = 100):
    """
    Generates a Sokoban room, represented by an integer matrix.
    """
    # Sometimes rooms with a score == 0 are the only possibility.
    # In these case, we try another model.
    for t in range(tries):
        room = RoomTopologyGenerator().generate(dim, p_change_directions, num_steps)
        room = place_boxes_and_player(room, num_boxes)
        room_state = room_structure = room.copy()
        room_structure[room_structure == 5] = 1
        room_state[room_state == 2] = 4   # 初始化，箱子就放在target上，然后逆向移动

        room_state, box_mapping, action_sequence = reverse_playing(room_state, room_structure, search_depth)
        room_state[room_state == 3] = 4
        if box_displacement_score(box_mapping) > 0:
            return room_structure, room_state, box_mapping, action_sequence
    raise RuntimeError("Generated Model with score == 0")
