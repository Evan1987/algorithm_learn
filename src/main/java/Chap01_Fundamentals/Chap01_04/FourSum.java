package Chap01_Fundamentals.Chap01_04;

import java.util.Arrays;

/**
 * (a + b) = - (c + d)
 * */
public class FourSum {
    public static int count(int[] v, int m){
        int N = v.length;
        int cnt = 0;
        for(int i = 0; i < N; i++){
            cnt += ThreeSum.count(Arrays.copyOfRange(v, i + 1, N), m - v[i]);
        }
        return cnt;
    }

    public static int count(int[] v){
        return count(v, 0);
    }

    public static void main(String[] args) {
        int[] v = {1, 2, 3, -4, 0, -3};
        System.out.println(count(v));
        // 1, 2, 0, -3
        // 1, 3, -4, 0
    }
}
