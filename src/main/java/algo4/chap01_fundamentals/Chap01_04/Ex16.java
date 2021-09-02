package algo4.chap01_fundamentals.Chap01_04;


import java.util.Arrays;

public class Ex16 {
    public static void closestPair(double[] a){
        Arrays.sort(a);
        double min = Double.MAX_VALUE;
        int index = 0;
        for(int i = 0; i < a.length - 1; i ++){
            double m1 = a[i];
            double m2 = a[i + 1];
            if(m2 - m1 < min){
                min = m2 - m1;
                index = i;
            }
        }
        System.out.println("The min difference is " + min);
        System.out.println("The min difference pair is " + a[index] + "and " + a[index + 1]);
    }

    public static void main(String[] args) {
        double[] a = {1,2,3,4,5,888,76,45};
        closestPair(a);
    }
}
