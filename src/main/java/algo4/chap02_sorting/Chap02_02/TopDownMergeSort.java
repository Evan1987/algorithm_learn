package algo4.chap02_sorting.Chap02_02;

import algo4.chap02_sorting.*;
import utils.annotations.WatchTime;

/**
 * @author Evan
 * @date 2020/2/23 17:51
 */

public class TopDownMergeSort extends Sorting {
    private Sorting sorter;

    public TopDownMergeSort(String type){
        switch (type.toLowerCase()){
            case "v1":
                this.sorter = new TopDownMergeSortV1();
                break;
            default:
                this.sorter = new TopDownMergeSortV2();
        }
    }

    public TopDownMergeSort(){
        this("");
    }

    @WatchTime(methodDesc = "TopDown merge sort")
    @Override
    public void sort(Comparable[] a) {
        this.sorter.sort(a);
    }

    public static void main(String[] args) {
        String[] arr = {"s", "o", "r", "t", "e", "x", "a", "m", "p", "l", "e"};
        TopDownMergeSort topDownMergeSort = new TopDownMergeSort("v2");
        test(topDownMergeSort, arr);
    }
}



class TopDownMergeSortV1 extends MergeSortingV1 {
    @Override
    public void sort(Comparable[] a) {
        this.initAux(a.length);
        this.sort(a, 0, a.length - 1);
    }

    private void sort(Comparable[] a, int lo, int hi){
        if(hi <= lo) return;
        int mid = lo + (hi - lo) / 2;
        this.sort(a, lo, mid);               // sort left
        this.sort(a, mid + 1, hi);        // sort right
        this.merge(a, lo, mid, hi);          // merge left and right
    }
}


class TopDownMergeSortV2 extends MergeSortingV2 {

    @Override
    public void sort(Comparable[] a) {
        Comparable[] aux = new Comparable[a.length];
        this.sort(a, 0, a.length - 1, aux);
    }

    public void sort(Comparable[] a, int lo, int hi, Comparable[] aux){
        if(hi <= lo) return;
        int mid = lo + (hi - lo) / 2;
        this.sort(a, lo, mid, aux);
        this.sort(a, mid + 1, hi, aux);
        this.merge(a, lo, mid, hi, aux);
    }
}
