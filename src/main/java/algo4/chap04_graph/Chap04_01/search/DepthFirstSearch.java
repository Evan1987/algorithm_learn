package algo4.chap04_graph.Chap04_01.search;

import algo4.chap01_fundamentals.Chap01_03.Queue;
import algo4.chap04_graph.Chap04_01.Graph;

/**
 * @author Evan
 * @date 2020/5/9 22:09
 * 深度优先搜索
 */
@SuppressWarnings("WeakerAccess")
public class DepthFirstSearch extends Search {
    protected boolean[] marked;
    protected int count;

    public DepthFirstSearch(Graph G, int s) {
        super(G, s);
        this.marked = new boolean[G.V()];
        this.dfs(G, s);
    }

    protected void dfs(Graph G, int v){
        marked[v] = true;
        this.count ++;
        for(int w: G.adj(v))
            if(!marked[w]) dfs(G, w);
    }

    // 深度优先非递归
    private void dfsWithLoop(Graph G, int source){
        Queue<Integer> queue = new Queue<>();
        queue.enqueue(source);
        while(!queue.isEmpty()){
            int v = queue.dequeue();
            if(this.marked[v]) continue;  // 访问过就略过

            // 没有访问过
            this.marked[v] = true;
            this.count ++;
            for(int w: G.adj(v))
                queue.enqueue(w);
        }
    }

    @Override
    public boolean marked(int v) {
        validateVertex(v);
        return this.marked[v];
    }

    @Override
    public int count() {
        return this.count;
    }

    public static void main(String[] args) {
        Graph g = Graph.generateGraph();
        System.out.println(g.degree(0));
        DepthFirstSearch search = new DepthFirstSearch(g, 0);
        search.test();
    }
}
