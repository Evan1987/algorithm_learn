package Chap04_Graph.Chap04_04;


import Chap04_Graph.Chap04_02.topological.Topological;

/**
 * @author Evan
 * @date 2020/5/23 21:35
 * 无环有向图的最短路径问题，利用图的拓扑排序
 */
public class AcyclicSP extends DijkstraSP {
    public AcyclicSP(EdgeWeightedDigraph G, int s){
        super(G, s);
    }

    @Override
    protected void action() {
        Topological topological = new Topological(this.G);
        if(!topological.hasOrder()) throw new IllegalArgumentException("DiGraph is not acyclic");
        for(int v: topological.order())
            for(DirectedEdge e: G.adjEdges(v))
                relax(e);
    }

    @Override
    protected void relax(DirectedEdge edge){
        int v = edge.from(), w = edge.to();
        double newDist = this.distTo[v] + edge.getWeight();
        if(this.distTo[w] > newDist){
            this.distTo[w] = newDist;
            this.edgeTo[w] = edge;
        }
    }

    public static void main(String[] args) {
        EdgeWeightedDigraph G = EdgeWeightedDigraph.generateGraph();
        int s = 0;
        AcyclicSP sp = new AcyclicSP(G, s);
        sp.test();
    }
}
