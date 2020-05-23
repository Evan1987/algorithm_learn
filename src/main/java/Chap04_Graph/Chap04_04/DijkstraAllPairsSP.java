package Chap04_Graph.Chap04_04;

/**
 * @author Evan
 * @date 2020/5/23 08:47
 */
public class DijkstraAllPairsSP {
    private DijkstraSP[] all;

    public DijkstraAllPairsSP(EdgeWeightedDigraph G){
        this.all = new DijkstraSP[G.V()];
        for(int v = 0; v < G.V(); v ++)
            this.all[v] = new DijkstraSP(G, v);
    }

    public Iterable<DirectedEdge> path(int s, int t){
        return this.all[s].pathTo(t);
    }

    public double dist(int s, int t){
        return this.all[s].distTo(t);
    }

    public boolean hasPath(int s, int t){
        return this.all[s].hasPathTo(t);
    }
}
