package Chap01Fundamentals.Chap01_02;

import java.text.MessageFormat;

public class Accumulator {
    public int N;
    public float total;
    public void addDataValue(double val){
        this.N ++;
        this.total += val;
    }

    public double mean(){
        return total / N;
    }

    public String toString(){
        return MessageFormat.format("Mean({0} values): {1, number, #.#}", N, mean());
    }
}
