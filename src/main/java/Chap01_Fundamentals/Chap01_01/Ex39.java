package Chap01_Fundamentals.Chap01_01;

import utils.URandom;
import utils.StdIn;
import utils.BinarySearch;

import java.util.Arrays;

public class Ex39 {
    public static void main(String[] args){
        int T = StdIn.readInt();
        int[] num = {1000, 10000, 100000, 1000000};
        int[] log = new int[num.length];
        for(int t = 0; t < T; t++){
            for (int i = 0; i < num.length; i++) {
                int N = num[i];
                int[] a = initArr(N);
                int[] b = initArr(N);
                Arrays.sort(a);
                for(int k: b){
                    if(BinarySearch.rank(k, a) != -1) log[i] ++;
                }
            }
        }
        System.out.println("Average Infos: ");
        for(int i = 0; i < num.length; i++){
            System.out.printf("%7d: %9.2f\n", num[i], (double) log[i] / T);
        }
    }

    private static int[] initArr(int N){
        URandom rng = new URandom();
        int[] a = new int[N];
        for(int i = 0; i < N; i++){
            a[i] = rng.uniform(100000, 1000000);
        }
        return a;
    }
}
