package Chap04_Graph.Chap04_01.paths;

import Chap01_Fundamentals.Chap01_03.Stack;
import Chap04_Graph.VertexMarkSearch;
import Chap04_Graph.Graph;

@SuppressWarnings("WeakerAccess")
public abstract class AbstractGraphPaths extends Paths implements VertexMarkSearch {

    public abstract int[] getEdgeTo();

    public AbstractGraphPaths(Graph G, int s) {
        super(G, s);
    }

    @Override
    public Iterable<Integer> pathTo(int v) {
        if(!this.hasPath(v)) return null;
        int[] edgeTo = this.getEdgeTo();
        Stack<Integer> path = new Stack<>();
        for(int x = v; x != this.getSource(); x = edgeTo[x])
            path.push(x);
        path.push(this.getSource());
        return path;
    }

    @Override
    public boolean hasPath(int v){
        this.validateVertex(v);
        return this.getMarked()[v];
    }
}
