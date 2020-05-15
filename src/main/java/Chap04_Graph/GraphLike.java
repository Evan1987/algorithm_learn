package Chap04_Graph;

public interface GraphLike {
    void addEdge(int v, int w);
    Iterable<Integer> adj(int v);

    default int E(){
        return 0;
    }

    default int V(){
        return 0;
    }

    int degree(int v);

}
