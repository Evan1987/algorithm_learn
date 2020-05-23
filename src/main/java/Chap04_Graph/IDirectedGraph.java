package Chap04_Graph;

/**
 * @author Evan
 * @date 2020-05-23 21:57:22
 * 有向图的基础接口
 * */
public interface IDirectedGraph extends IGraph {

    // 顶点的出度
    default int outDegree(int v){
        return this.degree(v);
    }

    // 顶点的入度
    int inDegree(int v);
}
