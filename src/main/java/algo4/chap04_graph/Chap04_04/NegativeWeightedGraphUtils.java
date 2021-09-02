package algo4.chap04_graph.Chap04_04;


/**
 * @author Evan
 * @date 2020/5/28 12:48
 */
public class NegativeWeightedGraphUtils {

    private static String[] edges(){
        return new String[]{
                "4 5 0.35", "5 4 0.35", "4 7 0.37", "5 7 0.28", "7 5 0.28", "5 1 0.32", "0 4 0.38", "0 2 0.26",
                "7 3 0.39", "1 3 0.29", "2 7 0.34", "6 2 -1.20", "3 6 0.52", "6 0 -1.40", "6 4 -1.25"
        };
    }

    public static EdgeWeightedDigraph getGraph(){
        EdgeWeightedDigraph graph = new EdgeWeightedDigraph(8);
        for(String s: edges()){
            String[] info = s.split(" ");
            DirectedEdge edge = new DirectedEdge(Integer.parseInt(info[0]), Integer.parseInt(info[1]), Double.parseDouble(info[2]));
            graph.addEdge(edge);
        }
        return graph;
    }

    public static edu.princeton.cs.algs4.EdgeWeightedDigraph getAlgGraph(){
        edu.princeton.cs.algs4.EdgeWeightedDigraph graph = new edu.princeton.cs.algs4.EdgeWeightedDigraph(8);
        for(String s: edges()){
            String[] info = s.split(" ");
            int v = Integer.parseInt(info[0]), w = Integer.parseInt(info[1]);
            double weight = Double.parseDouble(info[2]);
            edu.princeton.cs.algs4.DirectedEdge edge = new edu.princeton.cs.algs4.DirectedEdge(v, w, weight);
            graph.addEdge(edge);
        }
        return graph;
    }
}
