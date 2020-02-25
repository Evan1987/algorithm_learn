package Chap02_Sorting.Chap02_02;

import Chap02_Sorting.MergeSortingV2;
import Chap02_Sorting.Sorting;

/**
 * @author Evan
 * @date 2020/2/25 11:27
 * 次线性的额外空间，分块排序，逐一归并
 */
public class Ex12 {
    public static void main(String[] args) {
        String[] arr = {"s", "o", "r", "t", "e", "x", "a", "m", "p", "l", "e", "!"};
        int M = 4;
        BlockMerge blockMerge = new BlockMerge(M);
        Sorting.test(blockMerge, arr);
    }
}


class BlockMerge extends MergeSortingV2 {

    private int M;  // 分割块数

    public BlockMerge(int M){
        this.M = M;
    }

    @Override
    public void sort(Comparable[] a) {
        int N = a.length;
        assert N % this.M == 0: "The input size should be multiple times of M";
        Comparable[] aux = new Comparable[this.M];

        // Sort each block
        for(int i = 0; i < N; i += this.M)
            sort(a, aux, i, i + this.M - 1);

        // Selection sort between blocks by first element
        this.selectionSortBlocks(a);

        // Merge
        for(int i = 1; i < N / this.M; i ++)
            this.merge(a, 0, this.M * i - 1, this.M * (i + 1) - 1, aux);
    }

    /**
     * Sort block a[lo...hi] with aux array
     * */
    private void sort(Comparable[] a, Comparable[] aux, int lo, int hi){
        if(hi <= lo) return;
        int mid = lo + (hi - lo) / 2;
        sort(a, aux, lo, mid);
        sort(a, aux, mid + 1, hi);
        merge(a, lo, mid, hi, aux);
    }

    /**
     * Sort all blocks with its first element
     * */
    private void selectionSortBlocks(Comparable[] a){
        int N = a.length;
        for(int i = 0; i < N; i += this.M){
            int min = i;
            for(int j = i + this.M; j < N; j += this.M){
                if(less(a[j], a[min])) min = j;
            }

            // exchange block
            for(int k = 0; k < this.M; k ++)
                exchange(a, i + k, min + k);
        }
    }

    /**
     * 只使用 M长度 aux 进行 merge，copy
     * 倒序插入
     * */
    @Override
    public void merge(Comparable[] a, int lo, int sp, int hi, Comparable[] aux) {
        if(!less(a[sp + 1], a[sp])) return;

        int m = hi - sp;
        assert m <= this.M: "The right part length is beyond M";
        System.arraycopy(a, sp + 1, aux, 0, m);  // 只copy右半部分

        int i = sp, j = m - 1;
        for(int k = hi; k >= lo; k --){
            if(i < lo)
                a[k] = aux[j --];
            else if(j < 0)
                a[k] = a[i --];
            else
                a[k] = less(a[i], aux[j]) ? aux[j --] : a[i --];
        }
    }
}