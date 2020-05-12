package Chap04_Graph;

import edu.princeton.cs.algs4.In;
import java.util.*;

/**
 * @author Evan
 * @date 2020/5/8 21:54
 */
@SuppressWarnings("WeakerAccess")
public class Graph {
    private int V;                  // num of vertices
    private int E;                  // num of edges
    private List<Integer>[] adj;    // adjacent table, save the vertices for each vertex

    public Graph(){
        this.V = 0;
        this.E = 0;
        this.adj = initAdj(4);
    }

    public Graph(int V) {
        check(V);
        this.V = V;
        this.E = 0;
        this.adj = initAdj(V);
    }

    /**
     * Initialize from input stream.
     * The format as follows:
     * >>>
     * #V
     * #E
     * #vi #vj\n
     * #vk #vt\n
     * ...
     * */
    public Graph(In in){
        if(in == null) throw new IllegalArgumentException("Input stream is null");
        try{
            int V = in.readInt();
            check(V);
            this.V = V;
            this.adj = initAdj(V);

            int E = in.readInt();
            if(E < 0) throw new IllegalArgumentException("Number of edges must be non-negative");
            this.E = 0;

            for(int i = 0; i < E; i ++){
                int v = in.readInt();
                int w = in.readInt();
                validateVertex(v);
                validateVertex(w);
                addEdge(v, w);
            }
        }catch (NoSuchElementException e){
            throw new IllegalArgumentException("invalid input format in Graph constructor", e);
        }
    }

    public Graph(Graph G){
        this.V = G.V();
        this.E = G.E();
        this.adj = this.initAdj(this.V);

        for(int v = 0; v < this.V; v ++){
            for(int w: G.adj(v))
                this.adj[v].add(w);
        }
    }

    private void check(int V){
        if(V < 0) throw new IllegalArgumentException("Number of vertices must be non-negative");
    }

    private void validateVertex(int v){
        if(v < 0 || v >= this.V) throw new IllegalArgumentException("vertex " + v + " should be between 0 and " + (this.V - 1));
    }

    // extend adj array
    private void extendAdj(int V){
        List<Integer>[] temp = this.initAdj(V);
        for(int i = 0; i < this.adj.length; i ++)
            if(this.adj[i] != null) temp[i] = this.adj[i];
        this.adj = temp;
    }

    // add a pair into adj matrix
    private void addAdj(int target, int adjacent){
        if(this.adj[target] == null){
            this.adj[target] = new ArrayList<Integer>(){{
                add(adjacent);
            }};
            this.V ++;
        }
    }

    public void addEdge(int v, int w){
        this.E ++;
        if(Math.max(v, w) >= this.adj.length) extendAdj(Math.max(v, w) + 1);

        this.addAdj(v, w);
        if(v != w) this.addAdj(w, v);       // 防止自环时重复
    }

    @SuppressWarnings("unchecked")
    private List<Integer>[] initAdj(int V){
        return (List<Integer>[]) new ArrayList[V];
    }

    public int E(){
        return this.E;
    }

    public int V(){
        return this.V;
    }

    public Iterable<Integer> adj(int v){
        validateVertex(v);
        return this.adj[v] == null ? new ArrayList<>() : this.adj[v];
    }

    // 顶点 v的度数
    public int degree(int v){
        validateVertex(v);
        List<Integer> vertices = this.adj[v];
        return vertices == null ? 0 : vertices.size();
    }

    // 图的最大度数
    public int maxDegree(){
        int max = 0;
        for(int v = 0; v < this.V(); v ++)
            if(this.degree(v) > max)
                max = this.degree(v);
        return max;
    }

    // 图的平均度数
    public double avgDegree(){
        return 2.0 * this.E() / this.V();
    }

    // 自环的个数
    public int numOfSelfLoops(){
        int count = 0;
        for(int v = 0; v < this.V(); v ++)
            for(int w: this.adj(v))
                if(w == v) count ++;
        return count;
    }

    public static Graph generateGraph(){
        Graph g = new Graph();
        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(0, 5);
        g.addEdge(1, 2);
        g.addEdge(2, 3);
        g.addEdge(2, 4);
        g.addEdge(3, 4);
        g.addEdge(3, 5);
        return g;
    }

    public static void main(String[] args) {
        Graph g = Graph.generateGraph();
        System.out.println(g.V);
    }
}
