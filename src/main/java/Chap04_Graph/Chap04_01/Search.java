package Chap04_Graph.Chap04_01;

import Chap04_Graph.Graph;

/**
 * @author Evan
 * @date 2020/5/9 21:55
 * 联通搜索问题 API
 */
@SuppressWarnings("WeakerAccess")
public abstract class Search implements GraphProblem{
    private Graph graph;
    private final int source;

    // 给定图和顶点 S
    public Search(Graph G, int s){
        this.graph = G;
        this.source = s;
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

    public void validateVertex(int v){
        if(v <0 || v >= this.G().V())
            throw new IllegalArgumentException("vertex " + v + " is invalid!");
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
