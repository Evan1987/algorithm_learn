package Chap02_Sorting.Chap02_02;

import Chap02_Sorting.MergeSorting;
import utils.annotations.WatchTime;

/**
 * @author Evan
 * @date 2020/2/23 17:51
 */
public class TopDownMergeSort extends MergeSorting {

    @WatchTime(methodDesc = "TopDown merge sort")
    @Override
    public void sort(Comparable[] a) {
        this.initAux(a.length);
        sort(a, 0, a.length - 1);
    }

    private static void sort(Comparable[] a, int lo, int hi){
        if(hi <= lo) return;
        int mid = lo + (hi - lo) / 2;
        sort(a, lo, mid);               // sort left
        sort(a, mid + 1, hi);        // sort right
        merge(a, lo, mid, hi);          // merge left and right
    }

    public static void main(String[] args) {
        String[] arr = {"s", "o", "r", "t", "e", "x", "a", "m", "p", "l", "e"};
        TopDownMergeSort topDownMergeSort = new TopDownMergeSort();
        //test(insertionSort, arr);
        topDownMergeSort.sort(arr);
        for(String s: arr) System.out.print(s + " ");
    }
}
