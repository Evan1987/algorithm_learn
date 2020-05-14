package Chap04_Graph.Chap04_02.search;

import Chap04_Graph.Chap04_01.search.DepthFirstSearch;
import Chap04_Graph.Chap04_02.DiGraph;

/**
 * @author Evan
 * @date 2020/5/14 10:30
 */
@SuppressWarnings("WeakerAccess")
public class DirectedDFS extends DepthFirstSearch {

    public DirectedDFS(DiGraph G, int s){
        super(G, s);
    }

    public static void main(String[] args) {
        DiGraph g = DiGraph.generateGraph();
        int source = 0;
        DirectedDFS search = new DirectedDFS(g, source);

        // 0 -> 1
        // 0 -> 5 -> 4 -> 3 <-> 2
        for(int v = 0; v < g.V(); v ++)
            if(v != source) System.out.println("Source -> Vertex(" + v + "): " + search.marked[v]);
    }

}
