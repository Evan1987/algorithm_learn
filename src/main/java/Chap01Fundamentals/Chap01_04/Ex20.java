package Chap01Fundamentals.Chap01_04;

import utils.LocalSearch;
import utils.BinarySearch;

/**
 * 双调查找
 * */
public class Ex20 {

    static int bitonicSearch(int[] a, int t){
        // find the local maximum first
        int maximumIndex = LocalSearch.localMaximum(a);
        if(a[maximumIndex] < t) return -1;
        if(a[maximumIndex] == t) return maximumIndex;

        // find the key by binary search
        int rank = BinarySearch.rank(t, a, 0, maximumIndex);
        if(rank > 0) return rank;
        return BinarySearch.rank(t, a, maximumIndex, a.length);
    }

    public static void main(String[] args) {
        int[] a = {1, 5, 7, 9, 6, 4, 2};
        System.out.println(bitonicSearch(a, 4));  // 5
    }

}
