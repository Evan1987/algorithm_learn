package algo4.chap04_graph.Chap04_04;

import algo4.chap04_graph.Chap04_02.topological.Topological;

public class AcyclicLP extends AcyclicSP{
    public AcyclicLP(EdgeWeightedDigraph G, int s){
        super(G, s);
    }

    @Override
    protected void init(EdgeWeightedDigraph G, int s) {
        this.G = G;
        this.s = s;
        int N = G.V();
        this.distTo = new double[N];
        for(int v = 0; v < N; v ++)
            this.distTo[v] = Double.NEGATIVE_INFINITY;
        this.distTo[s] = 0.0;
        this.edgeTo = new DirectedEdge[N];
        Topological topological = new Topological(this.G);
        if(!topological.hasOrder()) throw new IllegalArgumentException("DiGraph is not acyclic");
        for(int v: topological.order())
            for(DirectedEdge e: G.adjEdges(v))
                relax(e);
    }

    @Override
    protected void relax(DirectedEdge e) {
        int v = e.from(), w = e.to();
        if (distTo[w] < distTo[v] + e.getWeight()) {
            distTo[w] = distTo[v] + e.getWeight();
            edgeTo[w] = e;
        }
    }
}
