package Chap04_Graph.Chap04_04;

import Chap01_Fundamentals.Chap01_03.Stack;
import Chap02_Sorting.Chap02_04.IndexMinPQ;
import Chap04_Graph.Edge;

/**
 * @author Evan
 * @date 2020/5/22 22:17
 */
public class DijkstraSP extends AbstractSP {

    protected double[] distTo;
    protected DirectedEdge[] edgeTo;
    protected IndexMinPQ<Double> pq;
    protected int s;
    protected EdgeWeightedDigraph G;

    public DijkstraSP(EdgeWeightedDigraph G, int s){
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
        this.pq = new IndexMinPQ<>(this.G.V());
        this.pq.insert(s, 0.0);
        while(!this.pq.isEmpty()){
            int v = pq.delMin();
            for(Edge e: G.adjEdges(v))
                this.relax((DirectedEdge) e);
        }

    }

    protected void relax(DirectedEdge edge){
        int v = edge.from(), w = edge.to();
        double newDist = this.distTo[v] + edge.getWeight();
        if(this.distTo[w] > newDist){
            this.distTo[w] = newDist;
            this.edgeTo[w] = edge;
            if(this.pq.contains(w)) this.pq.decreaseKey(w, newDist);
            else this.pq.insert(w, newDist);
        }
    }

    @Override
    public double distTo(int v) {
        return this.distTo[v];
    }

    @Override
    public boolean hasPathTo(int v) {
        return this.distTo[v] < Double.POSITIVE_INFINITY;
    }

    @Override
    public Iterable<DirectedEdge> pathTo(int v) {
        if(!this.hasPathTo(v)) return null;
        Stack<DirectedEdge> path = new Stack<>();
        for(DirectedEdge e = this.edgeTo[v]; e != null; e = this.edgeTo[e.from()])
            path.push(e);
        return path;
    }

    @Override
    public int getSourceVertex() {
        return this.s;
    }

    @Override
    public EdgeWeightedDigraph G() {
        return this.G;
    }

    public static void main(String[] args) {
        EdgeWeightedDigraph G = EdgeWeightedDigraph.generateGraph();
        int s = 0;
        DijkstraSP sp = new DijkstraSP(G, s);
        sp.test();
    }
}
