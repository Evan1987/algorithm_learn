import graphviz
from typing import *

START = 'I'
END = 'O'
PARALLEL = "||"
CASUALTY = "→"
CHOICE = "#"
SKIP = "△"
LOOP = "♢"


def normalize_trace(trace: List[str]) -> List[str]:
    if trace[0] != START:
        trace = [START] + trace
    if trace[-1] != END:
        trace = trace + [END]
    return trace


class AlphaMiner(object):

    def __init__(self):
        # {('a', 'b'): '->', ('b', 'c'): '||',...}
        self.footprint: Dict[Tuple[str, str], str] = {}
        # all connected
        self.node_predecessors: Dict[str, Set[str]] = {}
        self.node_successors: Dict[str, Set[str]] = {}
        # maximal candidates
        self.maximal_candidates: List[Tuple[Set[str], Set[str]]] = []
        # transitions
        self.transitions: Set[str] = set()
        # places
        self.places: Set[str] = set()
        # flow relations
        self.flow_relations: Dict[str, Set[str]] = {}

    def clear(self):
        self.footprint.clear()
        self.node_predecessors.clear()
        self.node_successors.clear()
        self.maximal_candidates.clear()
        self.transitions.clear()
        self.places.clear()
        self.flow_relations.clear()

    def generate_petri_net(self, orientation: str = 'LR') -> graphviz.Digraph:
        """Creating the Petri Net by Graphviz"""
        v = graphviz.Digraph()
        v.attr(rankdir=orientation, size='1000,1000')
        v.format = "png"
        # PetriNet transitions
        for transition in self.transitions:
            if transition == START:
                v.attr('node', width='0.3', fillcolor='#66FF66')
            elif transition == END:
                v.attr('node', width='0.3', fillcolor='#FF7C80')
            else:
                v.attr('node', width='0.5', fillcolor='white')
            v.attr('node', shape='box', fixedsize='true', style='filled',
                   fontname='arial black', fontsize='12', fontcolor='black')
            v.node(transition)

        # PetriNet places
        for place in self.places:
            color = "white"
            shape = "circle"
            if place == f"P\n{START}":
                color = "#66FF66"
            elif place == f"P\n{END}":
                color = "#FF7C80"
                shape = "doublecircle"
            v.attr('node', shape=shape, fillcolor=color)
            v.attr('node', fixedsize='true', width='0.6', style='filled',
                   fontname='arial', fontsize='10', fontcolor='black')
            v.node(place)

        # PetriNet flow relations
        for source, targets in self.flow_relations.items():
            for target in targets:
                v.edge(source, target)
        return v

    def _split_nodes(self, nodes: Iterable[str]) -> Iterable[Iterable[str]]:
        """
        Split collection of nodes into subsets in which ∀a,b∈subset_i:a#b
        :param nodes:The target collection of nodes to split
        :return: all subsets in which ∀a,b∈subset_i:a#b
        """
        graph: Dict[str, Set[str]] = {}
        node_list = list(nodes)
        for i in range(len(node_list)):
            source = node_list[i]
            for j in range(i + 1, len(node_list)):
                target = node_list[j]
                if (source, target) not in self.footprint and (target, source) not in self.footprint:  # e.g. '#'
                    graph.setdefault(source, set()).add(target)
                    graph.setdefault(target, set()).add(source)

        def bron_kerbosch(r: Set, p: Set, x: Set, c: List[Set]):
            if len(p) == 0 and len(x) == 0:
                # 找到一个极大团
                if r not in c:
                    c.append(r.copy())
                return

            pivot_vertex, _ = max([(v, len(graph.get(v, set()) & p)) for v in (p | x)], key=lambda t: t[1])

            for v in p - graph.get(pivot_vertex, set()):
                neighbors = graph.get(v, set())
                bron_kerbosch(r | {v},
                              p & neighbors,
                              x & neighbors,
                              cliques)
                p = p - {v}
                x = x | {v}

        # 初始化结果列表
        cliques: List[Set[str]] = []
        # 获取所有顶点
        vertices = set(nodes)
        # 调用递归函数
        bron_kerbosch(set(), vertices, set(), cliques)

        # 移除作为其他团子集的团
        maximal_cliques = []
        for c1 in cliques:
            for c2 in cliques:
                if c1 != c2 and c1.issubset(c2):
                    break
            else:
                maximal_cliques.append(c1)
        return maximal_cliques

    def _get_maximal_candidates(self) -> List[Tuple[Set[str], Set[str]]]:
        # nodes_self_connected: Set[str] = {node for node in nodes_all if self.footprint.get((node, node)) == "||"}
        # looking for the maximal candidates
        candidates: List[Tuple[Set[str], Set[str]]] = []
        # loop for all nodes, including I and O
        for a in self.transitions:
            # find max B(bi: a -> bi)
            b_set = self.node_successors.get(a, set())
            if not b_set:
                continue

            # find max A(ai: ai -> all B)
            a_set = set.intersection(*[self.node_predecessors[b] for b in b_set])
            if not a_set:
                continue

            # split a1 and a2 make sure: ∀a1,a2∈A:a1#a2, ∀b1,b2∈B:b1#b2
            a_set_cliques = self._split_nodes(a_set) if len(a_set) > 1 else [a_set]
            b_set_cliques = self._split_nodes(b_set) if len(b_set) > 1 else [b_set]

            # check if this element is maximal in candidates
            for a_set in a_set_cliques:
                for b_set in b_set_cliques:
                    for exist_a_set, exist_b_set in candidates[:]:
                        if a_set.issubset(exist_a_set) and b_set.issubset(exist_b_set):
                            break
                        elif exist_a_set.issubset(a_set) and exist_b_set.issubset(b_set):
                            candidates.remove((exist_a_set, exist_b_set))
                    else:
                        candidates.append((a_set, b_set))

        return candidates

    @staticmethod
    def fit_footprint(trace_list: List[List[str]]) -> Dict[Tuple[str, str], str]:
        arcs: Set[Tuple[str, str]] = set()
        # Alpha miner ignore the freq
        for trace in trace_list:
            for i in range(len(trace) - 1):
                arcs.add((trace[i], trace[i + 1]))
        footprint: Dict[Tuple[str, str], str] = {(x, y): PARALLEL if (y, x) in arcs else CASUALTY for (x, y) in arcs}
        return footprint

    def fit(self, trace_list: List[List[str]]):
        self.clear()
        trace_list = [normalize_trace(trace) for trace in trace_list]
        # Computing the alternative DFG representations: '->', '<-', '||' or '#'
        self.footprint = self.fit_footprint(trace_list)
        self.transitions.update(v for key in self.footprint for v in key)
        # Computing node_successor AND node_predecessors:
        for (a, b), label in self.footprint.items():
            if label == CASUALTY:
                self.node_successors.setdefault(a, set()).add(b)
                self.node_predecessors.setdefault(b, set()).add(a)

        # Computing maximal candidates, places and flow_relations
        self.maximal_candidates = self._get_maximal_candidates()
        for source_set, target_set in self.maximal_candidates:
            p = f"P\n{''.join(sorted(source_set))}_{''.join(sorted(target_set))}"
            self.places.add(p)
            for source in source_set:
                self.flow_relations.setdefault(source, set()).add(p)
            for target in target_set:
                self.flow_relations.setdefault(p, set()).add(target)
        # Add additional place for P_{START} and arc between P_{START} and START
        p = f"P\n{START}"
        self.places.add(p)
        self.flow_relations[p] = {START}
        #  # Add additional place for P_{END} and arc between END and P_{END}
        p = f"P\n{END}"
        self.places.add(p)
        self.flow_relations[END] = {p}


class AlphaPlusMiner(AlphaMiner):

    def __init__(self):
        super().__init__()
        self.l1l: Set[str] = set()
        self.l1l_predecessors: Dict[str, Set[str]] = {}
        self.l1l_successors: Dict[str, Set[str]] = {}

    @staticmethod
    def fit_footprint(trace_list: List[List[str]]) -> Dict[Tuple[str, str], str]:
        # Find length-1 loop e.g. L1L
        arcs: Set[Tuple[str, str]] = set()
        skips: Set[Tuple[str, str]] = set()
        for trace in trace_list:
            n = len(trace)
            for i in range(n - 1):
                # identify the skip relation
                if i < n - 2 and trace[i] == trace[i + 2]:
                    skips.add((trace[i], trace[i + 1]))
                arcs.add((trace[i], trace[i + 1]))

        footprint: Dict[Tuple[str, str], str] = {
            (x, y): CASUALTY if (y, x) not in arcs or ((x, y) in skips and (y, x) in skips) else PARALLEL
            for (x, y) in arcs
        }
        return footprint

    def fit(self, trace_list: List[List[str]]):
        self.clear()
        trace_list = [normalize_trace(trace) for trace in trace_list]
        # Find length-1 loop e.g. L1L
        for trace in trace_list:
            prev = trace[0]
            for i in range(1, len(trace)):
                cur = trace[i]
                if cur == prev:
                    self.l1l.add(cur)
                prev = cur

        new_trace_list = []
        for trace in trace_list:
            new_trace = [trace[0]]
            l1l_flag = False
            for i in range(1, len(trace)):
                cur = trace[i]
                cur_flag = cur in self.l1l
                if l1l_flag ^ cur_flag:  # if prev isn't l1l and cur is l1l | prev is l1l and cur isn't l1l
                    if cur_flag:
                        self.l1l_predecessors.setdefault(cur, set()).add(trace[i - 1])
                    else:
                        self.l1l_successors.setdefault(trace[i - 1], set()).add(cur)
                if not cur_flag:
                    new_trace.append(cur)
                l1l_flag = cur_flag
            new_trace_list.append(new_trace)

        super().fit(new_trace_list)
        self.transitions = self.transitions.union(self.l1l)
        # 合并连接在l1l集合上的relation
        for node in self.l1l:
            for source in self.l1l_predecessors[node]:
                places = self.flow_relations[source]
                for target in self.l1l_successors[node]:
                    for p in places:
                        # 找到合适的place
                        if target in self.flow_relations[p]:
                            # place到达点增加node
                            self.flow_relations[p].add(node)
                            # 该node的到达库所增加place
                            self.flow_relations.setdefault(node, set()).add(p)
                            break



