package algo4.chap04_graph.Chap04_02.paths;

import algo4.chap04_graph.Chap04_01.paths.BreadFirstPathsImpl;
import algo4.chap04_graph.Chap04_01.paths.DepthFirstPathsImpl;
import algo4.chap04_graph.Chap04_01.paths.Paths;
import algo4.chap04_graph.Chap04_02.DiGraph;

/**
 * @author Evan
 * @date 2020/5/14 14:44
 */
public class DirectedPathsImpl {

    private static void test(Paths search){
        for(int w: search.pathTo(2))
            System.out.print(w + "  ");                     // 0 5 4 3 2
        System.out.println("\n*************************");
        System.out.println(search.pathTo(11));           // null
    }


    public static void main(String[] args) {
        DiGraph G = DiGraph.generateGraph();
        int source = 0;
        DepthFirstDirectedPathsImpl dfsSearch = new DepthFirstDirectedPathsImpl(G, source);
        BreadFirstDirectedPathsImpl bfsSearch = new BreadFirstDirectedPathsImpl(G, source);

        System.out.println("DFS Test");
        test(dfsSearch);
        System.out.println();
        System.out.println("BFS Test");
        test(bfsSearch);

    }
}

class DepthFirstDirectedPathsImpl extends DepthFirstPathsImpl {
    DepthFirstDirectedPathsImpl(DiGraph G, int s) {
        super(G, s);
    }
}

class BreadFirstDirectedPathsImpl extends BreadFirstPathsImpl {
    BreadFirstDirectedPathsImpl(DiGraph G, int s){
        super(G, s);
    }
}