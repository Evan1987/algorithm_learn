package algo4.chap04_graph.Chap04_01.cycle;

import algo4.chap04_graph.GraphProblem;
import algo4.chap04_graph.Chap04_01.Graph;

import java.util.Stack;

/**
 * @author Evan
 * @date 2020/5/11 17:03
 * 检查图是否有环
 */
@SuppressWarnings("WeakerAccess")
public abstract class Cycle implements GraphProblem {
    public Cycle(Graph g){
        this.initWithGraph(g);
    }

    protected abstract void initWithGraph(Graph g);
    public abstract boolean hasCycle();
    public abstract Iterable<Integer> getCycle();   // return a cycle in the graph

    // 检查是否有自环，并返回一个自环
    protected Iterable<Integer> getSelfLoop(Graph G){
        for(int v = 0; v < G.V(); v ++){
            for(int w: G.adj(v)){
                if(v == w)
                    return new Stack<Integer>(){{
                        add(w);
                        add(w);
                    }};
            }
        }
        return null;
    }

    // 检查是否有平行边，并返回一个平行边
    protected Iterable<Integer> getParallelEdges(Graph G){
        boolean[] marked = new boolean[G.V()];
        for(int v = 0; v < G.V(); v ++){

            // 检查是否有重复出现的邻接接点
            for(int w: G.adj(v)){
                if(marked[w]){
                    Stack<Integer> cycle = new Stack<>();
                    cycle.add(v);
                    cycle.add(w);
                    cycle.add(v);
                    return cycle;
                }
                marked[w] = true;
            }

            for(int w: G.adj(v))
                marked[w] = false;
        }
        return null;
    }

    @Override
    public String problemDesc() {
        return "Check if the graph has cycle(including self-cycle and parallel edges)";
    }

    @Override
    public void test() {
        System.out.println("The graph has cycle ? " + this.hasCycle());
        if(this.hasCycle()){
            for(int v: this.getCycle())
                System.out.print(v + " ");
        }
    }
}
