package Chap04_Graph.Chap04_04;

import Chap01_Fundamentals.Chap01_03.Queue;
import Chap01_Fundamentals.Chap01_03.Stack;
import Chap04_Graph.Chap04_02.cycle.DirectedCycle;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Evan
 * @date 2020/5/28 14:29
 */
public class BellmanFordSP extends AbstractSP {

    private EdgeWeightedDigraph G;
    private int source;
    private double[] distTo;
    private DirectedEdge[] edgeTo;
    private boolean[] inQueue;                      // Indicate whether vertex already in the queue
    private Queue<Integer> queue;                   // queue of vertices to relax
    private int cost;                               // num of calls to relax()
    private Iterable<Integer> negativeCycle;   // negative cycle if exists

    public BellmanFordSP(EdgeWeightedDigraph G, int s){
        super(G, s);
    }

    @Override
    protected void init(EdgeWeightedDigraph G, int s) {
        this.G = G;
        this.source = s;
        int N = G.V();
        this.distTo = new double[N];
        for(int v = 0; v < N; v ++)
            this.distTo[v] = Double.POSITIVE_INFINITY;
        this.distTo[s] = 0.0;

        this.edgeTo = new DirectedEdge[N];
        this.inQueue = new boolean[N];
        this.queue = new Queue<>();
        enqueue(s);
        while (!hasNegativeCycle() && !queue.isEmpty()){
            int v = dequeue();
            this.relax(G, v);
        }
    }

    private void enqueue(int v){
        if(!this.inQueue[v]){
            this.queue.enqueue(v);
            this.inQueue[v] = true;
        }
    }

    private int dequeue(){
        int v = this.queue.dequeue();
        this.inQueue[v] = false;
        return v;
    }

    private void relax(EdgeWeightedDigraph G, int v){
        for(DirectedEdge e: G.adjEdges(v)){
            int w = e.to();
            // 只要一条边发生relax，则其终点被加入队列（如果没有加入队列）
            if(this.distTo[w] > this.distTo[v] + e.getWeight()){
                this.distTo[w] = this.distTo[v] + e.getWeight();
                this.edgeTo[w] = e;
                if(!this.inQueue[w]) enqueue(w);
            }

            // 周期性检查是否有负权重环
            if(++ this.cost % G.V() == 0){
                this.negativeCycle = this.findNegativeCycle();
                if(this.hasNegativeCycle()) return;
            }
        }
    }

    @Override
    public double distTo(int v) {
        checkNegativeCycle();
        return this.distTo[v];
    }

    @Override
    public boolean hasPathTo(int v) {
        return this.distTo[v] != Double.POSITIVE_INFINITY;
    }

    @Override
    public Iterable<DirectedEdge> pathTo(int v) {
        checkNegativeCycle();
        if(!hasPathTo(v)) return null;
        Stack<DirectedEdge> path = new Stack<>();
        for(DirectedEdge e = this.edgeTo[v]; e != null; e = this.edgeTo[e.from()])
            path.push(e);
        return path;
    }

    @Override
    public int getSourceVertex() {
        return this.source;
    }

    @Override
    public EdgeWeightedDigraph G() {
        return this.G;
    }

    public boolean hasNegativeCycle(){
        return this.negativeCycle != null;
    }

    private void checkNegativeCycle(){
        if(hasNegativeCycle()) throw new UnsupportedOperationException("Negative cost cycle exists.");
    }

    private Iterable<Integer> findNegativeCycle(){
        EdgeWeightedDigraph spt = new EdgeWeightedDigraph(this.G.V());
        for(DirectedEdge edge: this.edgeTo)
            if(edge != null) spt.addEdge(edge);
        DirectedCycle finder = new DirectedCycle(spt);
        return finder.getCycle();
    }

    public Iterable<Integer> getNegativeCycle() {
        return this.negativeCycle;
    }

    public static void main(String[] args) {
        EdgeWeightedDigraph graph = NegativeWeightedGraphUtils.getGraph();
        BellmanFordSP sp = new BellmanFordSP(graph, 1);
        sp.test();
        System.out.println("Total relax cost: " + sp.cost);

        edu.princeton.cs.algs4.EdgeWeightedDigraph algGraph = NegativeWeightedGraphUtils.getAlgGraph();
        edu.princeton.cs.algs4.BellmanFordSP algSP = new edu.princeton.cs.algs4.BellmanFordSP(algGraph, 1);
        System.out.println(algSP.distTo(5));
        for(edu.princeton.cs.algs4.DirectedEdge e: algSP.pathTo(5))
            System.out.println(e);
    }
}
