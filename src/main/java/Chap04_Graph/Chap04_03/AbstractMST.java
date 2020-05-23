package Chap04_Graph.Chap04_03;

import Chap04_Graph.Edge;

/**
 * @author Evan
 * @date 2020/5/17 20:13
 */
public abstract class AbstractMST {

    public AbstractMST(EdgeWeightedGraph graph){
        this.initWithGraph(graph);
    }

    protected abstract void initWithGraph(EdgeWeightedGraph graph);

    // 最小生成树的所有边
    public abstract Iterable<Edge> edges();
    // 最小生成树的总权重
    public double weight(){
        double w = 0.0;
        for(Edge e: this.edges())
            w += e.getWeight();
        return w;
    }
}
