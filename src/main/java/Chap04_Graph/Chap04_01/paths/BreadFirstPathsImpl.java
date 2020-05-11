package Chap04_Graph.Chap04_01.paths;

import Chap01_Fundamentals.Chap01_03.Queue;
import Chap04_Graph.Graph;

/**
 * @author Evan
 * @date 2020/5/11 13:08
 */
public class BreadFirstPathsImpl extends AbstractGraphPaths{
    private boolean[] marked;
    private int[] edgeTo;           // the shortest s-v path, like {@link DepthFirstPaths}
    private int[] distTo;           // number of edges for shortest s-v path

    public BreadFirstPathsImpl(Graph G, int s) {
        super(G, s);
        this.marked = new boolean[G.V()];
        this.edgeTo = new int[G.V()];
        this.distTo = new int[G.V()];
        for(int v = 0; v < G.V(); v ++)
            this.distTo[v] = Integer.MAX_VALUE;
        this.distTo[s] = 0;
        this.bfs(G, s);
    }

    private void bfs(Graph G, int s){
        Queue<Integer> q = new Queue<>();       // FIFO
        this.marked[s] = true;
        q.enqueue(s);
        while(!q.isEmpty()){
            int v = q.dequeue();
            for(int w: G.adj(v)){
                if(!this.marked[w]){
                    this.marked[w] = true;
                    this.edgeTo[w] = v;
                    this.distTo[w] = this.distTo[v] + 1;
                    q.enqueue(w);
                }
            }
        }
    }

    @Override
    public boolean[] getMarked() {
        return this.marked;
    }

    @Override
    public int[] getEdgeTo() {
        return this.edgeTo;
    }

    public static void main(String[] args) {
        Graph g = Graph.generateGraph();
        BreadFirstPathsImpl search = new BreadFirstPathsImpl(g, 0);
        search.test();
    }
}
