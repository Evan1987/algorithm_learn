package Chap04_Graph.Chap04_01.search;

import Chap04_Graph.GraphProblem;
import Chap04_Graph.Graph;

/**
 * @author Evan
 * @date 2020/5/9 21:55
 * 联通搜索问题 API
 */
@SuppressWarnings("WeakerAccess")
public abstract class Search implements GraphProblem {
    protected Graph graph;
    protected final int source;

    // 给定图和顶点 S
    public Search(Graph G, int s){
        this.graph = G;
        this.source = s;
        this.validateVertex(s);
    }

    // 判断顶点 V是否与S联通
    public abstract boolean marked(int v);

    // 与顶点s联通的顶点数量
    public abstract int count();

    public Graph G(){
        return this.graph;
    }

    public int getSource(){
        return this.source;
    }

    @Override
    public String problemDesc() {
        return "Find the connectivity of specified vertex.";
    }

    @Override
    public void test(){
        for(int v = 0; v < this.G().V(); v ++)
            if(this.marked(v))
                System.out.print(v + " ");

        System.out.println("\n*********");
        if(this.count() != this.G().V())
            System.out.println("Not connected");
        else
            System.out.println("connected");

    }

}
