package Chap04_Graph.Chap04_04;

import Chap04_Graph.Chap04_02.DiGraph;
import Chap04_Graph.Edge;
import Chap04_Graph.IWithEdge;
import utils.URandom;

import java.util.*;

/**
 * @author Evan
 * @date 2020/5/22 17:25
 */
public class EdgeWeightedDigraph extends DiGraph implements IWithEdge {
    protected List<DirectedEdge>[] adjEdges;
    protected List<DirectedEdge>[] inEdges;

    public EdgeWeightedDigraph(int V){
        super(V);
        this.adjEdges = initListArray(V);
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
            this.addEdge(v, w, weight);
        }
    }

    @Override
    public void addEdge(int v, int w) {
        this.addEdge(v, w, 1.0);
    }

    public void addEdge(int v, int w, double weight){
        this.addEdge(new DirectedEdge(v, w, weight));
    }

    public void addEdge(DirectedEdge e){
        int v = e.from(), w = e.to();
        this.validateVertex(v);
        this.validateVertex(w);
        this.adjEdges[v].add(e);
        this.inEdges[w].add(e);
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
    public List<DirectedEdge> adjEdges(int v){
        this.validateVertex(v);
        return this.adjEdges[v];
    }

    @Override
    public Iterable<DirectedEdge> edges() {
        List<DirectedEdge> edges = new ArrayList<>();
        for(List<DirectedEdge> es: this.adjEdges)
            edges.addAll(es);
        return edges;
    }

    public static String[] exampleEdges(){
        return new String[]{
                "4 5 0.35", "5 4 0.35", "4 7 0.37", "5 7 0.28", "7 5 0.28",
                "5 1 0.32", "0 4 0.38", "0 2 0.26", "7 3 0.39", "1 3 0.29",
                "2 7 0.34", "6 2 0.40", "3 6 0.52", "6 0 0.58", "6 4 0.93"
        };
    }

    public static EdgeWeightedDigraph generateGraph(){
        EdgeWeightedDigraph graph = new EdgeWeightedDigraph(8);
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
        for(int v = 0; v < graph.V(); v ++){
            System.out.print("Vertex: " + v + ": ");
            for(Edge e: graph.adjEdges(v))
                System.out.print(e + " | ");
            System.out.println();
        }

    }
}
