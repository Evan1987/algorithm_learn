package Chap04_Graph.Chap04_02;

import Chap04_Graph.Graph;
/**
 * Directed Graph
 * */
public class DiGraph extends Graph {
    protected int[] inDegree = new int[INIT_SIZE];

    public DiGraph(){
        super();
    }

    public DiGraph(int V){
        super(V);
    }

    public DiGraph(DiGraph G){
        super(G);
        this.inDegree = new int[G.V];
        System.arraycopy(G.inDegree, 0, this.inDegree, 0, G.V);
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
        return (1.0 * this.E) / this.V;
    }

    public DiGraph reverse(){
        DiGraph R = new DiGraph(this.V);
        for(int v = 0; v < this.V; v ++){
            for(int w: this.adj(v))
                R.addEdge(w, v);
        }
        return R;
    }

    public static void main(String[] args) {
        DiGraph g = new DiGraph();
        g.addEdge(0, 1); g.addEdge(0, 5); g.addEdge(2, 0);
        g.addEdge(2, 3); g.addEdge(3, 5); g.addEdge(3, 2);
        g.addEdge(4, 3); g.addEdge(4, 2); g.addEdge(5, 4);
        g.addEdge(6, 0); g.addEdge(6, 4); g.addEdge(6, 9);
        g.addEdge(7, 6); g.addEdge(7, 8); g.addEdge(8, 7);
        g.addEdge(8, 9); g.addEdge(9, 10); g.addEdge(9, 11);
        g.addEdge(10, 12); g.addEdge(11, 4); g.addEdge(11, 12);
        g.addEdge(12, 9);

        System.out.println("Num of total vertices: " + g.V());

        for(int v = 0; v < g.V(); v ++){
            System.out.println(v + ":");
            for(int w: g.adj(v)) System.out.print(w + " ");
            System.out.println("\n**************");
        }
    }
}
