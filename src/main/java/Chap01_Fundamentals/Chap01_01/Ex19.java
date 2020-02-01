package Chap01_Fundamentals.Chap01_01;

public class Ex19 {

    public static void main(String[] ars){
        System.out.println(F(4));
    }

    public static long F(int N){
        long[] fibonacci = new long[N + 1];
        fibonacci[0] = 0;
        fibonacci[1] = 1;
        if(N <= 1) return fibonacci[N];
        for(int i = 2; i <= N; i++){
            fibonacci[i] = fibonacci[i - 1] + fibonacci[i - 2];
        }
        return fibonacci[N];
    }
}
