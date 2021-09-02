package algo4.chap04_graph.Chap04_02.cycle;

import algo4.chap04_graph.Chap04_01.cycle.DepthFirstCycle;
import algo4.chap04_graph.Chap04_02.DiGraph;
import algo4.chap04_graph.Chap04_01.Graph;

import java.util.ArrayDeque;
import java.util.Deque;


/**
 * @author Evan
 * @date 2020/5/14 15:54
 */
public class DirectedCycle extends DepthFirstCycle {
    private boolean[] onStack;

    public DirectedCycle(DiGraph G){
        super(G);
    }

    @Override
    protected void initWithGraph(Graph graph) {
        this.onStack = new boolean[graph.V()];
        super.initWithGraph(graph);
    }

    // 有向图的平行边检查并不简单，所以此处覆盖
    @Override
    protected Iterable<Integer> getParallelEdges(Graph G){
        System.out.println("There's no parallel edges check any more for DG");
        return null;
    }

    @Override
    protected void dfs(Graph G, int u, int v) {
        onStack[v] = true;
        this.marked[v] = true;
        for(int w: G.adj(v)){
            // short circuit if cycle already found
            if(this.cycle != null) return;

            if(!this.marked[w]){
                this.edgeTo[w] = v;
                dfs(G, v, w);
            }
            // Found the cycle, the reverse edge is considered
            else if(onStack[w]){
                Deque<Integer> cycle = new ArrayDeque<>();
                // 回溯
                for(int x = v; x != w; x = this.edgeTo[x])
                    cycle.push(x);
                cycle.push(w);
                cycle.push(v);
                this.cycle = cycle;
                return;
            }
        }
        onStack[v] = false;
    }

    public static void main(String[] args) {
        System.out.println("Test for self-defined");

        DiGraph g = DiGraph.generateGraph();
        DirectedCycle search = new DirectedCycle(g);
        System.out.println(search.hasCycle());
        if(search.hasCycle())
            for(int w: search.getCycle())
                System.out.print(w + " ");

        System.out.println("\nTest for official");

        edu.princeton.cs.algs4.Digraph g2 = new edu.princeton.cs.algs4.Digraph(g.V());
        for(String edge: DiGraph.exampleEdges()){
            String[] points = edge.split("->");
            g2.addEdge(Integer.parseInt(points[0]), Integer.parseInt(points[1]));
        }
        edu.princeton.cs.algs4.DirectedCycle search2 = new edu.princeton.cs.algs4.DirectedCycle(g2);
        System.out.println(search2.hasCycle());
        if(search2.hasCycle())
            for(int w: search2.cycle())
                System.out.print(w + " ");


    }
}
