package Chap04_Graph.Chap04_01;

import Chap04_Graph.AbstractGraph;
import edu.princeton.cs.algs4.In;
import org.apache.commons.lang3.ArrayUtils;

import java.util.*;

/**
 * @author Evan
 * @date 2020/5/8 21:54
 */
@SuppressWarnings("WeakerAccess")
public class Graph extends AbstractGraph {
    protected final static int INIT_SIZE = 4;

    public Graph(){
        super();
    }

    public Graph(int V){
        super(V);
    }

    public Graph(In in){
        super(in);
    }

    public Graph(Graph g){
        super(g);
    }

    @Override
    protected void nullInit() {
        this.vertices = new HashSet<>(INIT_SIZE);
        this.E = 0;
        this.adj = initListArray(INIT_SIZE);
    }

    @Override
    protected void initWithV(int V) {
        if(V < 0) throw new IllegalArgumentException("Number of vertices must be non-negative");
        this.vertices = new HashSet<>(INIT_SIZE);
        this.E = 0;
        this.adj = initListArray(V);
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
    @Override
    protected void initWithInput(In in) {
        if(in == null) throw new IllegalArgumentException("Input stream is null");
        try{
            this.vertices = new HashSet<>(INIT_SIZE);
            int V = in.readInt();
            check(V);
            this.adj = initListArray(V);

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

    @Override
    protected void copyGraph(AbstractGraph G) {
        this.vertices = G.getVertices();
        this.E = G.E();
        this.adj = initListArray(G.V());

        for(int v = 0; v < G.V(); v ++){
            for(int w: G.adj(v))
                this.adj[v].add(w);
        }
    }

    private void check(int V){
        if(V < 0) throw new IllegalArgumentException("Number of vertices must be non-negative");
    }

    protected void validateVertex(int v){
        if(v < 0 || v >= this.V()) throw new IllegalArgumentException("vertex " + v + " should be between 0 and " + (this.V() - 1));
    }

    // extend array
    protected void extend(int V){
        List<Integer>[] temp = initListArray(V);
        System.arraycopy(this.adj, 0, temp, 0, this.adj.length);
        this.adj = temp;
    }

    // add a pair into adj matrix
    protected void addAdj(int target, int adjacent){
        this.vertices.add(target);
        this.vertices.add(adjacent);
        this.adj[target].add(adjacent);
    }

    @Override
    public void addEdge(int v, int w){
        this.E ++;
        if(Math.max(v, w) >= this.adj.length) extend(Math.max(v, w) + 1);

        this.addAdj(v, w);
        if(v != w) this.addAdj(w, v);       // 防止自环时重复
    }

    @SuppressWarnings("unchecked")
    protected static <T> List<T>[] initListArray(int V){
        List<T>[] adj = (List<T>[]) new ArrayList[V];
        for(int i = 0; i < V; i ++)
            adj[i] =  new ArrayList<>(INIT_SIZE);
        return adj;
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
        System.out.println(g.V());  // 6
    }
}
