package Chap04_Graph.Chap04_02.scc;

import Chap04_Graph.Chap04_01.cc.ConnectedComponent;
import Chap04_Graph.Chap04_02.DiGraph;
import Chap04_Graph.Chap04_02.topological.DepthFirstOrder;

/**
 * 有向图的强连通分量抽取
 * */
@SuppressWarnings("WeakerAccess")
public class KosarajuSharirSCC extends ConnectedComponent {
    private boolean[] marked;
    private int count;
    private int[] id;

    public KosarajuSharirSCC(DiGraph G){
        super(G);
        this.marked = new boolean[G.V()];
        this.id = new int[G.V()];

        /*
        * 采用反图的逆后序进行遍历，如果在 dfs(G, s)时遍历到 dfs(G, v)，则 s-v属于同一强连通分量
        * ① 若 dfs(G, s)时遍历到 dfs(G, v)，说明 G中存在 s->v的路径，且在GR图逆后序中，s在 v之前，即 GR图中 v先遍历完，然后 s遍历完
        * ② 在 GR中v先遍历完，然后 s遍历完有以下情况：
        *       a. dfs(GR, v) ... v结束   dfs(GR, s) ... s结束          （v,s不存在边连接）
        *       b. dfs(GR, s) ... dfs(GR, v) ... v结束 ... s结束         （存在 s -> v）
        * ③ 由于 G中存在 s->v则，GR中必然存在 v->s路径，则 a不可能发生，则只能发生 b情况，即 GR中存在 s->v，即 G中存在 v->s
        * */
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
        KosarajuSharirSCC scc = new KosarajuSharirSCC(g);
        scc.test();
    }
}
