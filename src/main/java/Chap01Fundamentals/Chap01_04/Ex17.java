package Chap01Fundamentals.Chap01_04;

public class Ex17 {
    public static void farthestPair(double[] a){
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
        for(double x: a){
            if(x < min) min = x;
            else if(x > max) max = x;
        }
        System.out.println("The farthest distance is " + (max - min));
        System.out.println("The farthest pair is " + min + " and " + max);
    }


    public static void main(String[] args) {
        double[] a = {1, 5, 8, -9, -5, 10};
        farthestPair(a);
    }
}
