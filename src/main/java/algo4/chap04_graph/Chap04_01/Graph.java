package algo4.chap04_graph.Chap04_01;

import algo4.chap04_graph.AbstractGraph;
import edu.princeton.cs.algs4.In;
import java.util.*;

/**
 * @author Evan
 * @date 2020/5/8 21:54
 */
public class Graph extends AbstractGraph {
    protected final static int INIT_SIZE = 4;

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
    protected void initWithV(int V) {
        check(V);
        this.V = V;
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
            int V = in.readInt();
            check(V);
            this.adj = initListArray(V);

            int E = in.readInt();
            if(E < 0) throw new IllegalArgumentException("Number of edges must be non-negative");
            this.E = 0;

            for(int i = 0; i < E; i ++){
                int v = in.readInt();
                int w = in.readInt();
                addEdge(v, w);
            }
        }catch (NoSuchElementException e){
            throw new IllegalArgumentException("invalid input format in Graph constructor", e);
        }
    }

    @Override
    protected void copyGraph(AbstractGraph G) {
        this.V = G.V();
        this.E = G.E();
        this.adj = initListArray(G.V());

        for(int v = 0; v < G.V(); v ++){
            for(int w: G.adj(v))
                this.adj[v].add(w);
        }
    }

    private static void check(int V){
        if(V < 0) throw new IllegalArgumentException("Number of vertices must be non-negative");
    }

    @Override
    public void addEdge(int v, int w){
        this.validateVertex(v);
        this.validateVertex(w);
        this.E ++;
        this.adj[v].add(w);
        if(v != w) this.adj[w].add(v);       // 防止自环时重复
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

    public static void main(String[] args) {
        Graph g = Graph.generateGraph();
        System.out.println(g.V());  // 6
    }
}
