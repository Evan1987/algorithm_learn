package Chap04_Graph;

import java.util.List;

/**
 * @author Evan
 * @date 2020/5/23 22:14
 */
public interface IWithEdge {
    // 获取具体的邻接边
    List<? extends Edge> adjEdges(int v);

    // 图中所有的边
    Iterable<? extends Edge> edges();
}
