package Chap04_Graph.Chap04_02.scc;

import Chap04_Graph.Chap04_01.cc.ConnectedComponent;
import Chap04_Graph.Chap04_02.DiGraph;
import Chap04_Graph.Chap04_02.topological.DepthFirstOrder;

public class KosarajuSCC extends ConnectedComponent {
    private boolean[] marked;
    private int count;
    private int[] id;

    public KosarajuSCC(DiGraph G){
        super(G);
        this.marked = new boolean[G.V()];
        this.id = new int[G.V()];
        DepthFirstOrder order = new DepthFirstOrder(G.reverse());
        for(int s: order.getReversePostOrder())
            if(!this.marked[s]){
                dfs(G, s); this.count ++;
            }
    }

    protected void dfs(DiGraph G, int v){
        this.marked[v] = true;
        this.id[v] = this.count;
        for(int w: G.adj(v))
            if(!this.marked[w]) dfs(G, w);
    }

    @Override
    public int componentCount() {
        return this.count;
    }

    @Override
    public int id(int v) {
        return this.id[v];
    }

    public static void main(String[] args) {
        DiGraph g = DiGraph.generateGraph();
        KosarajuSCC scc = new KosarajuSCC(g);
        scc.test();
    }
}
