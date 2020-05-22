package Chap04_Graph.Chap04_04;

import Chap04_Graph.Chap04_03.Edge;
import Chap04_Graph.Chap04_03.EdgeWeightedGraph;
import utils.URandom;

import java.util.*;

/**
 * @author Evan
 * @date 2020/5/22 17:25
 */
public class EdgeWeightedDigraph extends EdgeWeightedGraph {
    protected List<Edge>[] inEdges;

    public EdgeWeightedDigraph(){
        super();
        this.inEdges = initListArray(INIT_SIZE);
    }

    public EdgeWeightedDigraph(int V){
        super(V);
        this.inEdges = initListArray(V);
    }

    public EdgeWeightedDigraph(int V, int E){
        this(V);
        if(E < 0) throw new IllegalArgumentException("Number of edges must not be negative.");
        URandom rnd = new URandom();
        for(int i = 0; i < E; i ++){
            int v = rnd.uniform(V);
            int w = rnd.uniform(V);
            double weight = rnd.nextDouble();
            this.addEdge(new Edge(v, w, weight));
        }
    }

    @Override
    protected void extend(int V) {
        super.extend(V);
        this.inEdges = extendArr(this.inEdges, V);
    }

    @Override
    public void addEdge(Edge e){
        int v = e.getV(), w = e.getW();
        if(Math.max(v, w) >= this.adj.length) extend(Math.max(v, w) + 1);
        this.addAdj(v, w);
        this.adjEdges[e.getV()].add(e);
        this.inEdges[e.getW()].add(e);
        this.E ++;
    }

    public int outDegree(int v){
        this.validateVertex(v);
        return this.degree(v);
    }

    public int inDegree(int v){
        this.validateVertex(v);
        return this.inEdges[v].size();
    }

    @Override
    public Iterable<Edge> edges() {
        List<Edge> edges = new ArrayList<>();
        for(int v: this.vertices)
            edges.addAll(this.adjEdges[v]);
        return edges;
    }

    public static EdgeWeightedDigraph generateGraph(){
        EdgeWeightedDigraph graph = new EdgeWeightedDigraph();
        for(String edge: exampleEdges()){
            String[] line = edge.split(" ");
            graph.addEdge(Integer.parseInt(line[0]), Integer.parseInt(line[1]), Double.parseDouble(line[2]));
        }
        return graph;
    }

    public static void main(String[] args) {
        EdgeWeightedDigraph graph = generateGraph();
        System.out.println(graph.V());
        System.out.println(graph.E());
        for(Edge e: graph.adjEdges(1))
            System.out.println(e);
    }
}
