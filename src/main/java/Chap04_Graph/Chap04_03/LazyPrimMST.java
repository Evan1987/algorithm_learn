package Chap04_Graph.Chap04_03;

import Chap02_Sorting.Chap02_04.MinPQ;
import Chap04_Graph.Edge;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Evan
 * @date 2020/5/17 20:23
 */
public class LazyPrimMST extends AbstractMST {
    private List<Edge> edges;
    private boolean[] marked;
    private MinPQ<Edge> pq;

    public LazyPrimMST(EdgeWeightedGraph graph) {
        super(graph);
    }

    @Override
    protected void initWithGraph(EdgeWeightedGraph graph) {
        this.edges = new ArrayList<>();
        this.marked = new boolean[graph.V()];
        this.pq = new MinPQ<>();

        // 如果图不是连通的，则为 最小生成森林
        for(int v = 0; v < graph.V(); v ++)
            if(!marked[v]) prim(graph, v);
    }

    private void prim(EdgeWeightedGraph G, int s){
        scan(G, s);
        while(!pq.isEmpty()){
            Edge e = pq.pop();
            int v = e.either(), w = e.other(v);
            assert marked[v] || marked[w];              // 至少有一个scan过
            if(marked[v] && marked[w]) continue;        // lazy，有可能此时弹出，v和 w都 scan过了，因此会保留失效的边
            edges.add(e);
            if(!marked[v]) scan(G, v);                  // v成为树的一部分
            if(!marked[w]) scan(G, w);                  // w成为树的一部分
        }

    }

    // add all edges e incident to v into `pq` if the other endpoint has not been scanned yet
    private void scan(EdgeWeightedGraph G, int v){
        assert !marked[v];
        marked[v] = true;
        for(Edge e: G.adjEdges(v))
            if(!marked[e.other(v)]) pq.push(e);
    }

    @Override
    public Iterable<Edge> edges() {
        return this.edges;
    }

    public static void main(String[] args) {
        EdgeWeightedGraph graph = EdgeWeightedGraph.generateGraph();
        LazyPrimMST mst = new LazyPrimMST(graph);
        System.out.println("Total mst weight: " + mst.weight());
        for(Edge e: mst.edges())
            System.out.println(e);
    }
}
