package Chap04_Graph;

import edu.princeton.cs.algs4.In;

import java.util.List;
import java.util.Set;

/**
 * @author Evan
 * @date 2020/5/15 16:47
 */
public abstract class AbstractGraph implements GraphLike{

    protected List<Integer>[] adj;
    protected Set<Integer> vertices;
    protected int E;

    public AbstractGraph(){
        this.nullInit();
    }

    public AbstractGraph(int V){
        this.initWithV(V);
    }

    public AbstractGraph(In in){
        this.initWithInput(in);
    }

    public <T extends AbstractGraph> AbstractGraph(T G){
        this.copyGraph(G);
    }

    protected abstract void nullInit();
    protected abstract void initWithV(int V);
    protected abstract void initWithInput(In in);
    protected abstract void copyGraph(AbstractGraph G);

    @Override
    public int degree(int v) {
        return this.adj[v].size();
    }

    @Override
    public int V() {
        return this.vertices.size();
    }

    @Override
    public int E() {
        return this.E;
    }

    @Override
    public Iterable<Integer> adj(int v) {
        checkVertex(v);
        return this.adj[v];
    }

    public Set<Integer> getVertices() {
        return this.vertices;
    }

    protected void checkVertex(int v){
        if(!this.vertices.contains(v)) throw new IllegalArgumentException("Vertex " + v + " not in vertices.");
    }
}
