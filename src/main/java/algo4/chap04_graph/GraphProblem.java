package algo4.chap04_graph;

import algo4.chap04_graph.Chap04_01.Graph;

/**
 * @author Evan
 * @date 2020/5/10 22:03
 */
public interface GraphProblem {

    default String problemDesc(){return "empty";}
    Graph G();
    void test();

    default void validateVertex(int v){
        if(v <0 || v >= G().V())
            throw new IllegalArgumentException("vertex " + v + " is invalid!");
    }
}
