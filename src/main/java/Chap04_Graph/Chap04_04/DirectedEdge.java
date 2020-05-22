package Chap04_Graph.Chap04_04;

import Chap04_Graph.Chap04_03.Edge;

/**
 * @author Evan
 * @date 2020/5/22 17:02
 */
public class DirectedEdge extends Edge {

    public DirectedEdge(int v, int w, double weight) {
        super(v, w, weight);
        if(Double.isNaN(weight)) throw new IllegalArgumentException("Weight is NaN");
    }

    public int from(){
        return this.getV();
    }

    public int to(){
        return this.getW();
    }

    @Override
    public String toString() {
        return String.format("%d->%d %.5f", from(), to(), getWeight());
    }

    public static void main(String[] args) {
        DirectedEdge e = new DirectedEdge(12, 34, 5.67);
        System.out.println(e);
    }
}
