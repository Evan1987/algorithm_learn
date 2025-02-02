
import re
import numpy as np
import gym
from gym_sokoban.envs.sokoban_env import SokobanEnv
from .room_utils import generate_room
from typing import *


class Env(SokobanEnv):

    def __init__(self, dim: Tuple[int, int] = (7, 7), max_steps: int = 100, num_boxes: int = 3, search_depth: int = 100, **kwargs):
        super().__init__(dim_room=dim, max_steps=max_steps, num_boxes=num_boxes, **kwargs)
        self.cur_seq = []
        self.action_sequence = []
        self.search_depth = search_depth
        self.action_space = gym.spaces.discrete.Discrete(4, start=1)
        # after max_steps, game is over
        # search depth is the upper bound of the search strategy from dfs
        # search depth should be smaller than max steps to ensure the game can be finished

    @staticmethod
    def parse_update_info_to_obs(update_info, action_is_valid):
        observation, reward, done, _ = update_info
        if not action_is_valid:
            output_str = f"Action is invalid. You stay in the same position. The observation is: \n{observation}\nreward: {reward}\ndone: {done}\n"
        else:
            output_str = f"After you take this action, the observation is: \n{observation}\nreward: {reward}\ndone: {done}\n"
        return output_str

    @classmethod
    def execute_predictions(cls, envs, predictions: Any, pad_token):
        # print length of envs, predictions
        cur_actions, action_is_valid = cls.postprocess_predictions(predictions)
        next_obs = []
        for env, action, response, av in zip(envs, cur_actions, predictions, action_is_valid):
            # 1. check whether cur_response has the end token
            obs = ""
            # if "</answer>" not in response:
            #     obs += "</answer>"
            if "<|im_end|>" not in response:
                obs += "<|im_end|>"

            # 2. check whether the env is done
            if env.success():
                obs += pad_token
            else:
                env_feedback = cls.parse_update_info_to_obs(env.step(action), av)
                obs += "\n <|im_start|>user\n" + env_feedback + "<|im_end|>\n" + "<|im_start|>assistant\n<think>"
            next_obs.append(obs)
        return next_obs

    @staticmethod
    def extract_action(text):
        DIRECTION_MAP = {"Up": 1, "Down": 2, "Left": 3, "Right": 4}
        pattern = r'^\s*(([1-4])\s*\((up|down|left|right)\)|(up|down|left|right)|([1-4]))\s*$'
        match = re.fullmatch(pattern, text.strip(), flags=re.IGNORECASE | re.X)

        if not match:
            return 0

        if match.group(2):
            return int(match.group(2))
        elif match.group(4):
            return DIRECTION_MAP[match.group(4).capitalize()]
        elif match.group(5):
            return int(match.group(5))

        return 0

    @staticmethod
    def postprocess_predictions(predictions: Any):
        actions = []
        action_is_valid = []
        for prediction in predictions:
            if type(prediction) == str:
                # for llms
                if "<answer>" in prediction:
                    action = prediction.split("<answer>")[1].split("</answer>")[0].strip()
                else:
                    action = prediction.strip()

                action = Env.extract_action(action)
                if action == 0:
                    # print(f"[Invalid action]: \n{prediction}\n")
                    action_is_valid.append(False)
                else:
                    action_is_valid.append(True)

            # below is for non-llm strategies later
            elif isinstance(prediction, int):
                action = prediction if prediction in [1, 2, 3, 4] else 0
            elif isinstance(prediction, list):
                action = prediction
            elif prediction is None:
                # BFS did not find a solution
                action = 0
            else:
                raise ValueError(f"Invalid prediction type: {type(prediction)}")
            actions.append(action)
        return actions, action_is_valid

    def get_all_actions(self):
        return list(range(self.action_space.start, self.action_space.start + self.action_space.n))

    def reset(self, mode='tiny_rgb_array', seed=None):
        with NoLoggerWarnings():
            try:
                with set_seed(seed):
                    self.room_fixed, self.room_state, self.box_mapping, action_sequence = generate_room(
                        dim=self.dim_room,
                        num_steps=self.num_gen_steps,
                        num_boxes=self.num_boxes,
                        search_depth=self.search_depth
                    )
            except (RuntimeError, RuntimeWarning) as e:
                print("[SOKOBAN] Runtime Error/Warning: {}".format(e))
                print("[SOKOBAN] Retry . . .")
                next_seed = abs(hash(str(seed))) % (2 ** 32) if seed is not None else None
                return self.reset(mode, next_seed)
            # self.action_sequence = self._reverse_action_sequence(action_sequence)
            self.player_position = np.argwhere(self.room_state == 5)[0]
            self.num_env_steps = self.reward_last = self.boxes_on_target = 0
            return self.render(mode)

    def success(self):
        return self.boxes_on_target == self.num_boxes

    # def _reverse_action_sequence(self, action_sequence):
    #     def reverse_action(action):
    #         return (action % 2 + 1) % 2 + 2 * (action // 2) # 0 <-> 1, 2 <-> 3
    #     return [reverse_action(action) + 1 for action in action_sequence[::-1]] # action + 1 to match the action space

    def step(self, action: Union[int, list]):
        actions = [action] if isinstance(action, int) else action

        for act in actions:
            with NoLoggerWarnings():
                _, reward, done, _ = super().step(action, observation_mode='tiny_rgb_array')
            if done:
                break

        obs = self.render()
        return obs, reward, done, _

    def set_state(self, rendered_state):
        # from the rendered state, set the room state and player position
        self.room_state = np.where(rendered_state == 6, 5, rendered_state)
        self.player_position = np.argwhere(self.room_state == 5)[0]

    # Add rgb_array mode
    def render(self, mode='tiny_rgb_array'):
        assert mode in ['tiny_rgb_array', 'list', 'state', 'rgb_array']
        if mode == 'state':
            return np.where((self.room_state == 5) & (self.room_fixed == 2), 6, self.room_state)

        room_state = self.render(mode='state').tolist()

        if mode == 'list':
            lookup = lambda cell: GRID_LOOKUP.get(cell, "?").strip("\t").strip()
            return [" ".join(lookup(cell) for cell in row) for row in room_state]

        if mode == 'tiny_rgb_array':
            lookup = lambda cell: GRID_LOOKUP.get(cell, "?")
            return "\n".join("".join(lookup(cell) for cell in row) for row in room_state)

        if mode == 'rgb_array':
            img = self.get_image(mode, scale=1)
            return img

    def copy(self):
        new_self = SokobanEnv(
            dim_room=self.dim_room,
            max_steps=self.max_steps,
            num_boxes=self.num_boxes,
            search_depth=self.search_depth
        )
        new_self.room_fixed = self.room_fixed.copy()
        new_self.room_state = self.room_state.copy()
        new_self.box_mapping = self.box_mapping.copy()
        new_self.action_sequence = self.action_sequence.copy()
        new_self.player_position = self.player_position.copy()
        return new_self

GRID_LOOKUP = {
    0: " # \t",  # wall
    1: " _ \t",  # floor
    2: " O \t",  # target
    3: " √ \t",  # box on target
    4: " X \t",  # box
    5: " P \t",  # player
    6: " S \t",  # player on target
    # Use tab separator to separate columns and \n\n to separate rows.
}

ACTION_LOOKUP = {
    0: "none",
    1: "up",
    2: "down",
    3: "left",
    4: "right",
}

GUIDE = """
### Sokoban Puzzle Instructions

In Sokoban, your goal is to move all the boxes to the target spots on the grid. This requires careful planning and strategic moves. Here's how it works:

---

#### Symbols and Their Meaning
- **Walls (`#`)**: These block movement. You can't move through or push anything into walls.
- **Floor (`_`)**: Open spaces where you can walk and move boxes.
- **Targets (`O`)**: The spots where boxes need to go.
- **Boxes (`X`)**: These are what you need to push onto the targets.
- **Player (`P`)**: That's you! You'll move around the grid to push boxes.
- **Box on Target (`√`)**: A box successfully placed on a target.
- **Player on Target (`S`)**: You standing on a target.

---

#### Your Goal
Push all the boxes (`X`) onto the target spots (`O`). Once all boxes are on targets, you win!

---

#### Rules to Remember
1. **You Can Only Push Boxes**: You can't pull them, so plan ahead to avoid getting stuck.
2. **No Moving Through Walls**: You can't walk through or push boxes into walls (`#`).
3. **Avoid Traps**: Don't push boxes into corners or against walls where they can't be moved again.

---

#### Controls
Use these outputs to move the player:
- `1`: Move **up**.
- `2`: Move **down**.
- `3`: Move **left**.
- `4`: Move **right**.

#### Rewards
- **Move**: Each step you take costs 0.1.
- **Push Box to Target**: Each box placed on a target gives you 1.0.
- **Achieve Goal**: When all boxes are on targets, you get a reward of 10.0.

---

#### Example Map
Here's an example of a Sokoban puzzle:

# 	 # 	 # 	 # 	 # 	 # 	 # 	 
# 	 _ 	 _ 	 # 	 # 	 # 	 # 	 
# 	 _ 	 # 	 # 	 # 	 O 	 # 	 
# 	 _ 	 _ 	 _ 	 O 	 _ 	 # 	 
# 	 _ 	 X 	 X 	 _ 	 _ 	 # 	 
# 	 _ 	 O 	 _ 	 X 	 P 	 # 	 
# 	 # 	 # 	 # 	 # 	 # 	 # 	 

Each puzzle will have a different layout, but the rules and goal remain the same.

---

#### Tips for Beginners
1. **Move Boxes Step by Step**: Push them one at a time toward the targets.
2. **Think Ahead**: Avoid pushing a box into a spot where you can’t move it again.

Enjoy the challenge!
"""

if __name__ == '__main__':
    print(GUIDE)





