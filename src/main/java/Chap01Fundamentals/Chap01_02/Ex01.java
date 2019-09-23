package Chap01Fundamentals.Chap01_02;

import java.text.MessageFormat;
import utils.URandom;

public class Ex01 {
    public static Point[] generate(int N){
        Point[] res = new Point[N];
        URandom rng = new URandom();
        for(int i = 0; i < N; i++){
            res[i] = new Point(rng.nextDouble(), rng.nextDouble());
        }
        return res;
    }

    public static void main(String[] args){
        int N = 10;
        Point[] points = generate(N);
        Double minDist = Double.MAX_VALUE;
        Point[] closestPoints = new Point[2];
        for(int i = 0; i < N; i++){
            Point a = points[i];
            for(int j = i + 1; j < N; j++){
                Point b = points[j];
                Double dist = a.distTo(b);
                if(dist < minDist){
                    closestPoints[0] = a;
                    closestPoints[1] = b;
                    minDist = dist;
                }
            }
        }
        System.out.println(closestPoints[0] + "v" + closestPoints[1] + ": " + minDist);
    }

}


class Point{
    private double x;
    private double y;
    public Point(double x, double y){
        this.x = x;
        this.y = y;
    }

    public double distTo(Point other){
        return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
    }

    @Override
    public String toString() {
        return MessageFormat.format("({0, number, #.###},{1, number, #.###})",
                this.x, this.y);
    }
}