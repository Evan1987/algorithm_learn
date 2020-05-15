package Chap04_Graph.Chap04_03;

import org.jetbrains.annotations.NotNull;

/**
 * @author Evan
 * @date 2020/5/15 14:16
 */
public class Edge implements Comparable<Edge> {
    private final int v;
    private final int w;
    private final double weight;

    public Edge(int v, int w, double weight){
        checkVertex(v);
        checkVertex(w);
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    public Edge(int v, int w){
        this(v, w, 1.0);
    }

    public double getWeight() {
        return weight;
    }

    public int either(){
        return v;
    }

    public int other(int vertex){
        if(vertex == this.v) return this.w;
        if(vertex == this.w) return this.v;
        throw new IllegalArgumentException("Illegal endpoint");
    }

    private void checkVertex(int v){
        if(v < 0) throw new IllegalArgumentException("Vertex index must not be negative.");
    }

    @Override
    public String toString() {
        return String.format("%d-%d %.5f", v, w, weight);
    }

    @Override
    public int compareTo(@NotNull Edge that) {
        return Double.compare(this.weight, that.weight);
    }

    public static void main(String[] args) {
        Edge e = new Edge(12, 32, 6.57);
        System.out.println(e);
    }
}
