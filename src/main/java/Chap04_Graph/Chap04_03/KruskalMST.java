package Chap04_Graph.Chap04_03;

import Chap01_Fundamentals.Chap01_05.QuickUnionUF;
import Chap01_Fundamentals.Chap01_05.UnionFind;
import Chap02_Sorting.Chap02_04.MinPQ;
import Chap04_Graph.Edge;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Evan
 * @date 2020/5/22 14:20
 */
public class KruskalMST extends AbstractMST{

    private List<Edge> mst;

    public KruskalMST(EdgeWeightedGraph graph){
        super(graph);
    }

    @Override
    protected void initWithGraph(EdgeWeightedGraph graph) {
        mst = new ArrayList<>();
        MinPQ<Edge> pq = new MinPQ<>();
        for(Edge e: graph.edges())
            pq.push(e);

        UnionFind uf = new QuickUnionUF(graph.V());
        while(!pq.isEmpty() && mst.size() < graph.V() - 1){
            Edge e = pq.pop();
            int v = e.either(), w = e.other(v);
            if(uf.find(v) != uf.find(w)){
                uf.union(v, w);
                mst.add(e);
            }
        }

    }

    @Override
    public Iterable<Edge> edges() {
        return this.mst;
    }

    public static void main(String[] args) {
        EdgeWeightedGraph graph = EdgeWeightedGraph.generateGraph();
        KruskalMST mst = new KruskalMST(graph);
        System.out.println("Total mst weight: " + mst.weight());
        for(Edge e: mst.edges())
            System.out.println(e);
    }
}
