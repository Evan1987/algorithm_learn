package algo4.chap01_fundamentals.Chap01_02;

import java.text.MessageFormat;

public class Accumulator {
    private int N;
    private double total;
    private double m;  // mean
    private double s;  // var

    public void addDataValue(double val){
        this.N ++;
        this.total += val;
        this.s += 1.0 * (this.N - 1) / this.N * (val - this.m) * (val - this.m);
        this.m += (val - this.m) / this.N;
    }

    public double mean(){
        return m;
    }
    public double var() {
        return this.s / (this.N - 1);
    }
    public double stddev(){
        return Math.sqrt(this.var());
    }

    public String toString(){
        return MessageFormat.format("Mean({0} values): {1, number, #.#}", N, mean());
    }
}
