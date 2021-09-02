package algo4.chap01_fundamentals.Chap01_04;

import utils.BinarySearch;

import java.util.Arrays;

public class ThreeSum {

    /**
     * O(N^3)
     * */
    public static int fooCount(int[] v, int m){
        int N = v.length;
        int cnt = 0;
        long sum;
        for(int i = 0; i < N; i++){
            for(int j = i + 1; j < N; j++){
                for(int k = j + 1; k < N; k++){
                    sum = v[i] + v[j];
                    if(sum == m - v[k]) cnt ++;
                }
            }
        }
        return cnt;
    }

    public static int fooCount(int[] v){
        return fooCount(v, 0);
    }

    /**
     * O(N^2logN)
     * */
    public static int binaryCount(int[] v, int m){
        Arrays.sort(v);
        int N = v.length;
        int cnt = 0;
        long sum;
        for(int i = 0; i < N; i++){
            for(int j = i + 1; j < N; j++){
                if(BinarySearch.rank(m -v[i] - v[j], v) > j){
                    cnt ++;
                }
            }
        }
        return cnt;
    }


    public static int binaryCount(int[] v){
        return binaryCount(v, 0);
    }

    public static int count(int[] v, int m){
        int N = v.length;
        int cnt = 0;
        for(int i = 0; i < N - 2; i ++){
            cnt += TwoSum.fastCount(Arrays.copyOfRange(v, i + 1, N), m - v[i]);
        }
        return cnt;
    }

    public static int count(int[] v){
        return count(v, 0);
    }

    public static void main(String[] args) {
        int[] v = {1, 5, 2, -5, -6, 0, -3};
        System.out.println(fooCount(v));
        System.out.println(count(v));
    }

}
