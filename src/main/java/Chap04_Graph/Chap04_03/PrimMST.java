package Chap04_Graph.Chap04_03;

import Chap02_Sorting.Chap02_04.IndexMinPQ;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Evan
 * @date 2020/5/17 20:40
 * 任何一条连接 非树节点w的横切边都是与树权重最小的边，因此每加入一个顶点 v，与之直接连接的非树顶点 w的边权重会刷新，只保留一条最小权重的
 */
public class PrimMST extends AbstractMST {

    private Edge[] edgeTo;          // edgeTo[v] = shortest edge from tree vertex to non-tree vertex
    private double[] distTo;        // distTo[v] = weight of shortest such edge
    private boolean[] marked;       // marked[v] = true if v on tree, false otherwise
    private IndexMinPQ<Double> pq;      // 依赖的 PQ类型更高级，可以支持 update key

    public PrimMST(EdgeWeightedGraph graph) {
        super(graph);
    }

    @Override
    protected void initWithGraph(EdgeWeightedGraph graph) {
        int N = graph.V();
        this.edgeTo = new Edge[N];
        this.distTo = new double[N];
        this.marked = new boolean[N];
        this.pq = new IndexMinPQ<>(N);
        for(int i = 0; i < N; i ++)
            this.distTo[i] = Double.POSITIVE_INFINITY;

        // 如果图不是连通的，则为 最小生成森林 Ex4.3.22
        for(int v: graph.getVertices())
            if(!marked[v]) prim(graph, v);
    }

    private void prim(EdgeWeightedGraph G, int s){
        distTo[s] = 0.0;
        pq.insert(s, 0.0);
        while(!pq.isEmpty()){
            int v = pq.delMin();
            scan(G, v);
        }
    }

    private void scan(EdgeWeightedGraph G, int v){
        assert !marked[v];
        marked[v] = true;
        for(Edge e: G.adjEdges(v)){
            int w = e.other(v);
            if(marked[w]) continue;
            if(e.getWeight() < distTo[w]){
                edgeTo[w] = e;
                distTo[w] = e.getWeight();
                if(pq.contains(w))
                    pq.decreaseKey(w, e.getWeight());           // 覆盖现有的连接 w的边，只保留一条最小的。即时的体现
                else
                    pq.insert(w, e.getWeight());
            }
        }
    }

    @Override
    public Iterable<Edge> edges() {
        List<Edge> edges = new ArrayList<>();
        for(Edge e: edgeTo)
            if(e != null) edges.add(e);
        return edges;
    }

    public static void main(String[] args) {
        EdgeWeightedGraph graph = EdgeWeightedGraph.generateGraph();
        PrimMST mst = new PrimMST(graph);
        System.out.println("Total mst weight: " + mst.weight());
        for(Edge e: mst.edges())
            System.out.println(e);
    }
}
