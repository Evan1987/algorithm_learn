package Chap04_Graph.Chap04_01.cc;

import Chap04_Graph.Chap04_01.VertexMarkSearch;
import Chap04_Graph.Graph;

/**
 * @author Evan
 * @date 2020/5/11 16:35
 * 用深度优先寻找连通分量
 */
@SuppressWarnings("WeakerAccess")
public class DepthFirstCC extends ConnectedComponent implements VertexMarkSearch {
    private boolean[] marked;
    private int[] id;               // vertex -> component
    private int[] componentSize;    // component -> num of vertices
    private int componentCount;

    public DepthFirstCC(Graph G){
        super(G);
        this.marked = new boolean[G.V()];
        this.id = new int[G.V()];
        this.componentSize = new int[G.V()];
        this.componentCount = 0;
        for(int v = 0; v < G.V(); v ++)
            if(!marked[v]){
                this.dfs(G, v);             // 在一个连通分量中进行深度搜索
                this.componentCount ++;     // 一旦跳出，意味着一个连通分量搜索已完成
            }
    }

    private void dfs(Graph G, int v){
        this.marked[v] = true;
        int componentIndex = this.componentCount;
        this.id[v] = componentIndex;
        this.componentSize[componentIndex] ++;
        for(int w: G.adj(v))
            if(!this.marked[w]) dfs(G, w);
    }

    @Override
    public boolean[] getMarked() {
        return this.marked;
    }

    @Override
    public int componentCount() {
        return this.componentCount;
    }

    @Override
    public int id(int v) {
        this.validateVertex(v);
        return this.id[v];
    }

    // Return the num of vertices of the component where `v` at.
    public int size(int v){
        return this.componentSize[this.id[v]];
    }

    public static void main(String[] args) {
        Graph g = new Graph(5);
        g.addEdge(0, 1);
        g.addEdge(2, 3);
        g.addEdge(3, 4);

        DepthFirstCC search = new DepthFirstCC(g);
        search.test();
    }
}
