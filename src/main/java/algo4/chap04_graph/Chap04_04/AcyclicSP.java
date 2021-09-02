package algo4.chap04_graph.Chap04_04;


import algo4.chap04_graph.Chap04_02.topological.Topological;

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
    protected void init(EdgeWeightedDigraph G, int s) {
        this.G = G;
        this.s = s;
        int N = G.V();
        this.distTo = new double[N];
        for(int v = 0; v < N; v ++)
            this.distTo[v] = Double.POSITIVE_INFINITY;
        this.distTo[s] = 0.0;
        this.edgeTo = new DirectedEdge[N];
        Topological topological = new Topological(this.G);
        if(!topological.hasOrder()) throw new IllegalArgumentException("DiGraph is not acyclic");
        for(int v: topological.order())
            for(DirectedEdge e: G.adjEdges(v))
                relax(e);
    }

    @Override
    protected void relax(DirectedEdge e){
        int v = e.from(), w = e.to();
        if (distTo[w] > distTo[v] + e.getWeight()) {
            distTo[w] = distTo[v] + e.getWeight();
            edgeTo[w] = e;
        }
    }

    public static void main(String[] args) {
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(8);
        G.addEdge(5, 4, 0.35);
        G.addEdge(4, 7, 0.37);
        G.addEdge(5, 7, 0.28);
        G.addEdge(5, 1, 0.32);
        G.addEdge(4, 0, 0.38);
        G.addEdge(0, 2, 0.26);
        G.addEdge(3, 7, 0.39);
        G.addEdge(1, 3, 0.29);
        G.addEdge(7, 2, 0.34);
        G.addEdge(6, 2, 0.40);
        G.addEdge(3, 6, 0.52);
        G.addEdge(6, 0, 0.58);
        G.addEdge(6, 4, 0.93);
        int s = 5;
        AcyclicSP sp = new AcyclicSP(G, s);
        sp.test();
    }
}
