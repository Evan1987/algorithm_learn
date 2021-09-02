package algo4.chap01_fundamentals.Chap01_04;

import utils.BinarySearch;

import java.util.Arrays;

/**
 * O(N * logN)
 * */
public class TwoSum {

    /**
     * a + b = k;
     * */
    public static int fastCount(int[] v, int k){
        int N = v.length;
        int cnt = 0;
        Arrays.sort(v);
        for(int i = 0; i < N; i++){
            if(BinarySearch.rank(k - v[i], v) > i) cnt ++;
        }
        return cnt;
    }

    /**
     * a + b = 0
     * */
    public static int fastCount(int[] v){
        return fastCount(v, 0);
    }
}
