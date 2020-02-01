package Chap01_Fundamentals.Chap01_02;

import utils.StdIn;

public class Ex02 {
    public static void main(String[] args){

        int N = 3;
        Interval1D[] intervals = new Interval1D[N];
        for (int i = 0; i < N; i ++){
            String[] input = StdIn.readString().split(",");
            intervals[i] = new Interval1D(Double.parseDouble(input[0]), Double.parseDouble(input[1]));
        }

        for (int i = 0; i < N; i ++){
            for(int j = i + 1; j < N; j ++ ){
                if(intervals[i].intersect(intervals[j])){
                    System.out.println(intervals[i] + " intersects " + intervals[j]);
                }
            }
        }
    }

}


class Interval1D {
    private double start;
    private double end;

    public Interval1D(double x, double y){
        this.start = x;
        this.end = y;
    }

    public boolean intersect(Interval1D that){
        return (that.start <= this.start & this.start <= that.end) |
                (that.start <= this.end  & this.end <= that.end);
    }

    @Override
    public String toString() {
        return "(" + this.start + "-" + this.end + ")";
    }
}