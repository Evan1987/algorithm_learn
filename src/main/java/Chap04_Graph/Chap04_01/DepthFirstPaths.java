package Chap04_Graph.Chap04_01;

import Chap01_Fundamentals.Chap01_03.Stack;
import Chap04_Graph.Graph;

/**
 * @author Evan
 * @date 2020/5/10 22:24
 */
@SuppressWarnings("WeakerAccess")
public class DepthFirstPaths extends Paths {

    private boolean[] marked;
    // 一棵树, index为起点， value为终点。 example: edgeTo[2] = 0，说明从 2 可以到 0
    // 记录了从任意节点回到 source的路径
    private int[] edgeTo;

    public DepthFirstPaths(Graph G, int s) {
        super(G, s);
        this.marked = new boolean[G.V()];
        this.edgeTo = new int[G.V()];
        this.dfs(G, s);
    }

    private void dfs(Graph G, int v){
        this.marked[v] = true;
        for(int w: G.adj(v))
            if(!marked[w]){
                this.edgeTo[w] = v;     // 与 search相比多了此步骤
                dfs(G, w);
            }
    }

    @Override
    public boolean hasPath(int v) {
        return this.marked[v];  // 与search问题相同
    }

    @Override
    public Iterable<Integer> pathTo(int v) {
        if(!this.hasPath(v)) return null;
        Stack<Integer> path = new Stack<>();
        for(int x = v; x != this.getSource(); x = this.edgeTo[x])
            path.push(x);
        path.push(this.getSource());
        return path;
    }

    public static void main(String[] args) {
        Graph g = Graph.generateGraph();
        DepthFirstPaths search = new DepthFirstPaths(g, 0);
        search.test();
    }
}
