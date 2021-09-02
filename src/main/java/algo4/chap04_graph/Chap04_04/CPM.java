package algo4.chap04_graph.Chap04_04;


public class CPM {

    private static String[] tasks(){
        return new String[]{
                "0 41.0 1 7 9", "1 51.0 2", "2 50.0", "3 36.0", "4 38.0",
                "5 45.0", "6 21.0 3 8", "7 32.0 3 8", "8 32.0 2", "9 29.0 4 6"
        };
    }

    public static void main(String[] args) {
        int N = 10;
        // use Weighted edge denoting the task duration
        // add additional dummy node of self to support the edge, the dummy node is `self + N`
        // add dummy global start node as `2N`, global end as `2N + 1`
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(2 * N + 2);
        int start = 2 * N, end = 2 * N + 1;
        for(String task: tasks()){
            String[] info = task.split(" ");
            int s = Integer.parseInt(info[0]), dummy = s + N;
            double duration = Double.parseDouble(info[1]);
            G.addEdge(start, s, 0.0);
            G.addEdge(s, dummy, duration);
            G.addEdge(dummy, end, 0.0);

            if(info.length > 2)
                for(int j = 2; j < info.length; j ++)
                    G.addEdge(dummy, Integer.parseInt(info[j]), 0.0);
        }

        AcyclicLP lp = new AcyclicLP(G, start);
        System.out.println("The start time for each task: ");
        for(int i = 0; i < N; i ++)
            System.out.printf("%5d: %5.1f\n", i, lp.distTo(i));
        System.out.println("The finish time: " + lp.distTo(end));
    }
}
