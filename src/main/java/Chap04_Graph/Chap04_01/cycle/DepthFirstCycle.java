package Chap04_Graph.Chap04_01.cycle;

import Chap04_Graph.VertexMarkSearch;
import Chap04_Graph.Chap04_01.Graph;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author Evan
 * @date 2020/5/11 17:09
 * 使用深度优先判定图是否含有环
 */
public class DepthFirstCycle extends Cycle implements VertexMarkSearch {
    protected boolean[] marked;
    protected int[] edgeTo;
    protected Iterable<Integer> cycle;

    public DepthFirstCycle(Graph graph){
        this.graph = graph;
        this.cycle = this.getSelfLoop(graph);
        if(cycle != null) {
            System.out.println("There's self loop!");
            return;
        }
        this.cycle = this.getParallelEdges(graph);
        if(cycle != null){
            System.out.println("There's parallel edges!");
            return;
        }

        this.marked = new boolean[graph.V()];
        this.edgeTo = new int[graph.V()];
        for(int v = 0; v < graph.V(); v ++)
            if(!this.marked[v] && cycle == null) dfs(graph, -1, v);
    }

    // u: the start, v: the target
    protected void dfs(Graph G, int u, int v){
        this.marked[v] = true;
        for(int w: G.adj(v)){
            // short circuit if cycle already found
            if(this.cycle != null) return;

            if(!this.marked[w]){
                this.edgeTo[w] = v;
                dfs(G, v, w);
            }
            // Found the cycle, but disregard the reverse of edge v-w
            else if(w != u){
                Deque<Integer> cycle = new ArrayDeque<>();
                // 回溯
                for(int x = v; x != w; x = this.edgeTo[x])
                    cycle.push(x);
                cycle.push(w);
                cycle.push(v);
                this.cycle = cycle;
                return;
            }
        }
    }

    @Override
    public boolean[] getMarked() {
        return this.marked;
    }

    @Override
    public boolean hasCycle() {
        return this.cycle != null;
    }

    @Override
    public Iterable<Integer> getCycle() {
        return this.cycle;
    }

    public static void main(String[] args) {
        Graph g = new Graph(5);
        g.addEdge(1, 2);
        g.addEdge(1, 3);
        g.addEdge(1, 4);
        g.addEdge(2, 3);

        DepthFirstCycle search = new DepthFirstCycle(g);
        search.test();
    }
}
