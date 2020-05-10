package Chap04_Graph;

import edu.princeton.cs.algs4.In;
import java.util.*;

/**
 * @author Evan
 * @date 2020/5/8 21:54
 */
@SuppressWarnings("WeakerAccess")
public class Graph {
    private final int V;                  // num of vertices
    private int E;                  // num of edges
    private List<Integer>[] adj;    // adjacent table, save the vertices for each vertex

    public Graph(int V) {
        check(V);
        this.V = V;
        this.E = 0;
        initAdj(V);
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
            initAdj(V);

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
        this.initAdj(this.V);

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

    private void addEdge(int v, int w){
        this.E ++;
        this.adj[v].add(w);
        if(v != w){     // 防止自环时重复
            this.adj[w].add(v);
        }
    }

    @SuppressWarnings("unchecked")
    private void initAdj(int V){
        this.adj = (ArrayList<Integer>[]) new ArrayList[V];
        for(int i = 0; i < V; i ++)
            this.adj[i] = new ArrayList<>();
    }

    public int E(){
        return this.E;
    }

    public int V(){
        return this.V;
    }

    public Iterable<Integer> adj(int v){
        validateVertex(v);
        return this.adj[v];
    }

    // 顶点 v的度数
    public int degree(int v){
        validateVertex(v);
        return this.adj[v].size();
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
        Graph g = new Graph(6);
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
}
