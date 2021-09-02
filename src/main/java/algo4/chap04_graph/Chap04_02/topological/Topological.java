package algo4.chap04_graph.Chap04_02.topological;

import algo4.chap04_graph.Chap04_02.DiGraph;
import algo4.chap04_graph.Chap04_02.cycle.DirectedCycle;
import algo4.chap04_graph.Chap04_01.Graph;
import algo4.chap04_graph.GraphProblem;

/**
 * @author Evan
 * @date 2020/5/14 17:23
 */
@SuppressWarnings("WeakerAccess")
public class Topological implements GraphProblem {
    protected Graph G;
    private DepthFirstOrder dfs;

    public Topological(DiGraph G){
        this.G = G;
        DirectedCycle cycleFinder = new DirectedCycle(G);
        if(!cycleFinder.hasCycle()) this.dfs = new DepthFirstOrder(G);
    }

    @Override
    public String problemDesc() {
        return "Find the topological order for DAG.";
    }

    @Deprecated
    public boolean isDAG(){
        return this.hasOrder();
    }
    public boolean hasOrder(){
        return this.dfs != null;
    }

    public Iterable<Integer> preOrder(){
        if(this.dfs != null) return this.dfs.getPreOrder();
        return null;
    }

    public Iterable<Integer> postOrder(){
        if(this.dfs != null) return this.dfs.getPostOrder();
        return null;
    }

    public Iterable<Integer> reversePostOrder(){
        if(this.dfs != null) return this.dfs.getReversePostOrder();
        return null;
    }

    public Iterable<Integer> order(){
        return this.reversePostOrder();
    }

    @Override
    public Graph G() {
        return this.G;
    }

    @Override
    public void test() {
        System.out.println(this.isDAG());
        if(this.isDAG()){
            System.out.println("\nPre Order");
            for(int w: this.preOrder()) System.out.print(w + " ");
            System.out.println("\nPost Order");
            for(int w: this.postOrder()) System.out.print(w + " ");
            System.out.println("\nReverse Post Order");
            for(int w: this.reversePostOrder()) System.out.print(w + " ");
        }
    }

    public static void main(String[] args) {
        DiGraph g = new DiGraph(13);
        g.addEdge(0, 1); g.addEdge(0, 5); g.addEdge(0, 6); g.addEdge(2, 0);
        g.addEdge(2, 3); g.addEdge(3, 5); g.addEdge(5, 4); g.addEdge(6, 4);
        g.addEdge(6, 9); g.addEdge(7, 6); g.addEdge(8, 7); g.addEdge(9, 10);
        g.addEdge(9, 11); g.addEdge(9, 12); g.addEdge(11, 12);

        Topological search = new Topological(g);
        search.test();
    }
}
