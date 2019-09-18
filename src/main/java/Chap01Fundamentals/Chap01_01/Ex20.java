package Chap01Fundamentals.Chap01_01;

public class Ex20 {

    private static double ln(int N){
        if(N == 0) return 0.;
        return Math.log(N) + ln(N - 1);
    }

    private static double ln2(int N){
        if(N < 1) return 0.;
        double res = 0.0;
        for(int i = 2; i <= N; i++){
            res += Math.log(i);
        }
        return res;
    }

    public static void main(String[] args){
        System.out.println(ln(10));
        System.out.println(ln2(10));
    }
}
