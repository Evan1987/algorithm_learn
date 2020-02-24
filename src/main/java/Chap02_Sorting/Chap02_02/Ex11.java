package Chap02_Sorting.Chap02_02;

import Chap02_Sorting.MergeSortingV2;
import Chap02_Sorting.Sorting;


/**
 * @author Evan
 * @date 2020/2/24 15:03
 * 改进归并排序，（1）小数组排序；（2）检测数组是否有序；（3）递归中使用交换而不是复制
 */
public class Ex11 {
    public static void main(String[] args) {
        String[] arr = {"s", "o", "r", "t", "e", "x", "a", "m", "p", "l", "e"};
        ImprovedMergeSort improvedMergeSort = new ImprovedMergeSort();
        Sorting.test(improvedMergeSort, arr);
    }

}

class ImprovedMergeSort extends MergeSortingV2 {
    private int THRESHOLD = 7;

    @Override
    public void sort(Comparable[] a) {
        Comparable[] aux = a.clone();
        this.sort(aux, 0, a.length - 1, a);  // 转换了 a 与 aux的角色
    }

    // 让 aux[lo...hi]有序
    public void sort(Comparable[] a, int lo, int hi, Comparable[] aux){

        // (1) 小数组走插入排序
        if(hi - lo < this.THRESHOLD){
            this.insertionSort(aux);
            return;
        }

        int mid = lo + (hi - lo) / 2;
        this.sort(aux, lo, mid, a);             // a 左半边有序
        this.sort(aux, mid + 1, hi, a);      // a 右半边有序

        // 当右侧头部比左侧尾部小才需merge，否则数组已经有序
        if(less(a[mid + 1], a[mid])){
            merge(a, lo, mid, hi, aux);
        }
    }

    @Override
    public void merge(Comparable[] a, int lo, int mid, int hi, Comparable[] aux){
        int i = lo, j = mid + 1;

        for(int k = lo; k <= hi; k ++){
            if(i > mid)
                aux[k] = a[j ++];
            else if (j > hi)
                aux[k] = a[i ++];
            else
                aux[k] = less(a[j], a[i]) ? a[j ++] : a[i ++];
        }
    }

    public void insertionSort(Comparable[] a) {
        for(int i = 1; i < a.length; i ++){
            for(int j = i; j > 0 && Sorting.less(a[j], a[j - 1]); j --)
                Sorting.exchange(a, j, j - 1);
        }
    }
}
