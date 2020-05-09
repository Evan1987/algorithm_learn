package Chap04_Graph;

/**
 * @author Evan
 * @date 2020/5/9 21:55
 */
public abstract class Search {
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

    public static void test(Search search){
        for(int v = 0; v < search.G().V(); v ++)
            if(search.marked(v))
                System.out.print(v + " ");

        System.out.println("\n*********");
        if(search.count() != search.G().V())
            System.out.println("Not connected");
        else
            System.out.println("connected");

    }

}
