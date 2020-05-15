package Chap04_Graph.Chap04_03;

import Chap04_Graph.Chap04_01.Graph;
import edu.princeton.cs.algs4.In;
import utils.URandom;
import java.util.*;

/**
 * @author Evan
 * @date 2020/5/15 14:26
 */
public class EdgeWeightedGraph extends Graph {
    protected List<Edge>[] adjEdges;

    public EdgeWeightedGraph(){
        super();
    }

    public EdgeWeightedGraph(int V){
        super(V);
        this.adjEdges = initListArray(V);
    }

    public EdgeWeightedGraph(int V, int E){
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

    public EdgeWeightedGraph(In in){
        this();
    }

    @Override
    public void addEdge(int v, int w) {
        this.addEdge(v, w, 1.0);
    }

    public void addEdge(int v, int w, double weight){
        Edge edge = new Edge(v, w, weight);
        this.addEdge(edge);
    }

    public void addEdge(Edge e){
        int v = e.either();
        int w = e.other(v);
        super.addEdge(v, w);
        this.adjEdges[v].add(e);
        this.adjEdges[w].add(e);
    }

    @Override
    protected void extend(int V) {
        super.extend(V);
        List<Edge>[] temp = initListArray(V);
        System.arraycopy(this.adjEdges, 0, temp, 0, this.adjEdges.length);
        this.adjEdges = temp;
    }

    public Iterable<Edge> edges(){
        List<Edge> list = new ArrayList<>();
        for(Integer v: this.vertices){
            int selfLoops = 0;
            for(Edge e: this.adjEdges[v]){
                if(e.other(v) > v) list.add(e);
                else if(e.other(v) == v){
                    // 防止自环多次加入队列
                    if(selfLoops % 2 == 0) list.add(e);
                    selfLoops ++;
                }
            }
        }
        return list;
    }

    public static String[] exampleEdges(){
        return new String[]{
                "4 5 0.35", "4 7 0.37", "5 7 0.28", "0 7 0.16", "1 5 0.32", "0 4 0.38", "2 3 0.17", "1 7 0.19",
                "0 2 0.26", "1 2 0.36", "1 3 0.29", "2 7 0.34", "6 2 0.40", "3 6 0.52", "6 0 0.58", "6 4 0.93"
        };
    }

    public static EdgeWeightedGraph generateGraph(){
        EdgeWeightedGraph graph = new EdgeWeightedGraph();
        return graph;
    }
}
