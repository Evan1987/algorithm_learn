package Chap04_Graph.Chap04_02;

import Chap04_Graph.Chap04_01.Graph;

/**
 * Directed Graph
 * */
@SuppressWarnings("WeakerAccess")
public class DiGraph extends Graph {
    protected int[] inDegree = new int[INIT_SIZE];

    public DiGraph(){
        super();
    }

    public DiGraph(int V){
        super(V);
        this.inDegree = new int[V];
    }

    public DiGraph(DiGraph G){
        super(G);
        this.inDegree = new int[G.V()];
        System.arraycopy(G.inDegree, 0, this.inDegree, 0, G.V());
    }

    @Override
    protected void extend(int V) {
        super.extend(V);
        int[] temp = new int[V];
        System.arraycopy(this.inDegree, 0, temp, 0, this.inDegree.length);
        this.inDegree = temp;
    }

    @Override
    public void addEdge(int v, int w) {
        this.E ++;
        if(Math.max(v, w) >= this.adj.length) extend(Math.max(v, w) + 1);
        this.addAdj(v, w);
        this.inDegree[w] ++;
    }

    public int outDegree(int v) {
        return super.degree(v);
    }

    public int inDegree(int v){
        this.validateVertex(v);
        return this.inDegree[v];
    }

    @Override
    public double avgDegree() {
        return (1.0 * this.E()) / this.V();
    }

    public DiGraph reverse(){
        DiGraph R = new DiGraph(this.V());
        for(int v = 0; v < this.V(); v ++)
            for(int w: this.adj(v))
                R.addEdge(w, v);
        return R;
    }

    public static String[] exampleEdges(){
        return new String[]{
                "0->1", "0->5", "2->0", "2->3", "3->5", "3->2", "4->3", "4->2",
                "5->4", "6->0", "6->4", "6->9", "7->6", "7->8", "8->7", "8->9",
                "9->10", "9->11", "10->12", "11->4", "11->12", "12->9"};
    }

    /**
     * Generate a sample Directed Graph
     */
    public static DiGraph generateGraph(){
        DiGraph g = new DiGraph();
        for(String edge: exampleEdges()){
            String[] points = edge.split("->");
            g.addEdge(Integer.parseInt(points[0]), Integer.parseInt(points[1]));
        }
        return g;
    }

    public static void main(String[] args) {

        DiGraph g = DiGraph.generateGraph();
        System.out.println("Num of total vertices: " + g.V());

        for(int v = 0; v < g.V(); v ++){
            System.out.println(v + ":");
            for(int w: g.adj(v)) System.out.print(w + " ");
            System.out.println("\n**************");
        }
    }
}
