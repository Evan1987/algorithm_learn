package Chap02_Sorting.Chap02_02;

import Chap02_Sorting.*;
import utils.annotations.WatchTime;

/**
 * @author Evan
 * @date 2020/2/24 09:29
 */
public class BottomUpMergeSort extends Sorting {

    private Sorting sorter;

    public BottomUpMergeSort(String type) {
        switch (type.toLowerCase()) {
            case "v1":
                this.sorter = new BottomUpMergeSortV1();
            default:
                this.sorter = new BottomUpMergeSortV2();
        }
    }

    public BottomUpMergeSort(){
        this("");
    }

    @WatchTime(methodDesc = "BottomUp merge sort")
    @Override
    public void sort(Comparable[] a) {
        this.sorter.sort(a);
    }

    public static void main(String[] args) {
        String[] arr = {"s", "o", "r", "t", "e", "x", "a", "m", "p", "l", "e"};
        BottomUpMergeSort bottomUpMergeSort = new BottomUpMergeSort();
        test(bottomUpMergeSort, arr);

    }
}


class BottomUpMergeSortV1 extends MergeSortingV1 {

    @Override
    public void sort(Comparable[] a) {
        int N = a.length;
        this.initAux(N);

        // move 2 * size each time
        for(int size = 1; size < N; size *= 2){
            int segmentLength = 2 * size;  // 预期合并的数组大小

            // |lo ------ mid ------hi|
            // |--- size --|-- size --|
            for(int lo = 0; lo < N - size; lo += segmentLength){  // 守卫确保 mid存在，即保证merge可行
                int hi = lo + segmentLength - 1;
                merge(a, lo, lo + size - 1, Math.min(hi, N - 1));
            }
        }
    }
}


class BottomUpMergeSortV2 extends MergeSortingV2 {

    @Override
    public void sort(Comparable[] a) {
        int N = a.length;
        Comparable[] aux = new Comparable[N];

        // move 2 * size each time
        for(int size = 1; size < N; size *= 2){
            int segmentLength = 2 * size;  // 预期合并的数组大小

            // |lo ------ mid ------hi|
            // |--- size --|-- size --|
            for(int lo = 0; lo < N - size; lo += segmentLength){  // 守卫确保 mid存在，即保证merge可行
                int hi = lo + segmentLength - 1;
                merge(a, lo, lo + size - 1, Math.min(hi, N - 1), aux);
            }
        }
    }
}
