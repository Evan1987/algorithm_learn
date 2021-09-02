package algo4.chap04_graph;

import edu.princeton.cs.algs4.In;
import java.util.List;

/**
 * @author Evan
 * @date 2020/5/15 16:47
 */
public abstract class AbstractGraph implements IGraph {

    protected List<Integer>[] adj;
    protected int E;
    protected int V;

    public AbstractGraph(int V){
        this.initWithV(V);
    }

    public AbstractGraph(In in){
        this.initWithInput(in);
    }

    public <T extends AbstractGraph> AbstractGraph(T G){
        this.copyGraph(G);
    }

    protected abstract void initWithV(int V);
    protected abstract void initWithInput(In in);
    protected abstract void copyGraph(AbstractGraph G);

    @Override
    public int degree(int v) {
        return this.adj[v].size();
    }

    @Override
    public int V() {
        return this.V;
    }

    @Override
    public int E() {
        return this.E;
    }

    @Override
    public Iterable<Integer> adj(int v) {
        validateVertex(v);
        return this.adj[v];
    }

    protected void validateVertex(int v){
        if(v < 0 || v >= this.V()) throw new IllegalArgumentException("Vertex " + v + " not in vertices.");
    }
}
