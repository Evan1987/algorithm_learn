package Chap04_Graph.Chap04_02.topological;

import Chap04_Graph.Chap04_02.DiGraph;

import java.util.*;

/**
 * @author Evan
 * @date 2020/5/14 17:42
 */
public class DepthFirstOrder {
    private boolean[] marked;
    private List<Integer> preOrder;               // 所有顶点的前序队列，就是dfs的调用顺序
    private List<Integer> postOrder;              // 所有顶点的后序队列，就是顶点遍历完成的顺序，越早完成，越靠前
    private Deque<Integer> reversePostOrder;      // 所有顶点的逆后序队列，就是顶点遍历完成的反序，越晚完成，越靠前

    public DepthFirstOrder(DiGraph G){
        this.preOrder = new ArrayList<>();
        this.postOrder = new ArrayList<>();
        this.reversePostOrder = new ArrayDeque<>();
        this.marked = new boolean[G.V()];
        for(int v = 0; v < G.V(); v ++)
            if(!this.marked[v]) dfs(G, v);
    }

    private void dfs(DiGraph G, int v){
        this.marked[v] = true;
        this.preOrder.add(v);
        for(int w: G.adj(v))
            if(!this.marked[w]) dfs(G, w);
        this.postOrder.add(v);
        this.reversePostOrder.push(v);
    }

    public List<Integer> getPostOrder() {
        return postOrder;
    }

    public List<Integer> getPreOrder() {
        return preOrder;
    }

    public Deque<Integer> getReversePostOrder() {
        return reversePostOrder;
    }
}
