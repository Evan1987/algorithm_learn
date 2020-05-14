package Chap04_Graph.Chap04_01.paths;

import Chap04_Graph.Graph;

/**
 * @author Evan
 * @date 2020/5/10 22:24
 */
@SuppressWarnings("WeakerAccess")
public class DepthFirstPathsImpl extends AbstractGraphPaths {

    protected boolean[] marked;
    // 一棵树, index为起点， value为终点。 example: edgeTo[2] = 0，说明从 2 可以到 0
    // 记录了从任意节点回到 source的路径
    protected int[] edgeTo;

    public DepthFirstPathsImpl(Graph G, int s) {
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
    public int[] getEdgeTo() {
        return this.edgeTo;
    }

    @Override
    public boolean[] getMarked() {
        return this.marked;
    }

    public static void main(String[] args) {
        Graph g = Graph.generateGraph();
        DepthFirstPathsImpl search = new DepthFirstPathsImpl(g, 0);
        search.test();
    }
}
