package Chap04_Graph;

/**
 * 基本图接口
 * */
public interface IGraph {
    // 添加边的方法
    void addEdge(int v, int w);

    // 获取邻接节点的方法
    Iterable<Integer> adj(int v);

    // 获取图的总边数
    int E();

    // 获取图的总顶点数
    int V();

    // 获取顶点 v的度数（边数）
    int degree(int v);

}
