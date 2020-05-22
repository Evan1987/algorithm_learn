package Chap04_Graph.Chap04_04;

/**
 * @author Evan
 * @date 2020/5/22 21:46
 * 最短路径基础类
 */
public abstract class AbstractSP {

    public AbstractSP(EdgeWeightedDigraph G, int s){
        this.init(G, s);
    }

    protected abstract void init(EdgeWeightedDigraph G, int s);
    public abstract double distTo(int v);
    public abstract boolean hasPathTo(int v);
    public abstract Iterable<DirectedEdge> pathTo(int v);
    public abstract int getSourceVertex();
    public abstract EdgeWeightedDigraph G();

    public void test(){
        for(int v: this.G().getVertices()){
            System.out.println(this.getSourceVertex() + " to " + v + " " + this.distTo(v));
            if(this.hasPathTo(v))
                for(DirectedEdge e: this.pathTo(v))
                    System.out.print(e + " ");
            System.out.println("\n*****************************");
        }
    }
}
