package Chap04_Graph.Chap04_01.cc;

import Chap04_Graph.GraphProblem;
import Chap04_Graph.Chap04_01.Graph;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Evan
 * @date 2020/5/11 16:02
 * 连通分量的查询
 */
public abstract class ConnectedComponent implements GraphProblem {
    private Graph graph;

    public ConnectedComponent(Graph G){
        this.graph = G;
    }

    @Override
    public Graph G() {
        return this.graph;
    }

    public abstract int componentCount();             // 连通分量数
    public abstract int id(int v);                    // v所在的连通分量标识 0 - (componentCount - 1)

    // v与w是否连通
    public boolean connected(int v, int w){
        this.validateVertex(v);
        this.validateVertex(w);
        return this.id(v) == this.id(w);
    }

    /**
     * 获取每个连通分量的顶点
     * */
    @SuppressWarnings("unchecked")
    private List<Integer>[] getComponents(){
        int M = this.componentCount();
        List<Integer>[] components = (ArrayList<Integer>[]) new ArrayList[M];
        for(int i = 0; i < M; i ++)
            components[i] = new ArrayList<>();

        // 遍历所有顶点，放入各自的 component中
        for(int v = 0; v < this.G().V(); v ++)
            components[this.id(v)].add(v);

        return components;
    }

    @Override
    public String problemDesc() {
        return "Find the connection component of specified vertex";
    }

    @Override
    public void test() {
        int M = this.componentCount();
        System.out.println("There are " + M + " components.");
        List<Integer>[] components = this.getComponents();
        for(int i = 0; i < M; i ++){
            for(int v: components[i])
                System.out.print(v + " ");
            System.out.println("\n*********************");
        }
    }
}
