package algo4.chap02_sorting.Chap02_02;

import algo4.chap02_sorting.*;


/**
 * @author Evan
 * @date 2020/2/24 15:03
 * 改进归并排序，（1）小数组排序；（2）检测数组是否有序；（3）递归中使用交换而不是复制
 */
public class Ex11 {
    public static void main(String[] args) {
        String[] arr = {"h", "b", "c", "d", "e", "g", "f", "a", "i"};
        ImprovedMergeSort improvedMergeSort = new ImprovedMergeSort();
        Sorting.test(improvedMergeSort, arr);
    }

}

class ImprovedMergeSort extends MergeSortingV2 {
    private int THRESHOLD = 4;

    @Override
    public void sort(Comparable[] input) {
        Comparable[] b = input.clone();
        this.sort(input, b, 0, input.length - 1);
    }

    /**
     * 使用aux辅助数组让 a[lo...hi]有序
     * */
    public void sort(Comparable[] a, Comparable[] aux, int lo, int hi){

        // (1) 小数组走插入排序
        int length = hi + 1 - lo;
        if(length <= this.THRESHOLD){
            this.insertionSort(a, lo, hi);
            return;
        }

        int mid = lo + (hi - lo) / 2;
        this.sort(aux, a, lo, mid);             // aux 左半边有序
        this.sort(aux, a, mid + 1, hi);      // aux 右半边有序
        merge(a, lo, mid, hi, aux);             // 将有序的aux数组 merge回a
    }

    /**
     * 将 aux中已排序数据 merge 到 a
     * */
    @Override
    public void merge(Comparable[] a, int lo, int mid, int hi, Comparable[] aux){
        // 当右侧头部不小于左侧尾部说明aux已经有序，只要从 aux copy回 a即可，不用再走for循环逐一判断
        if(!less(aux[mid + 1], aux[mid])){
            System.arraycopy(aux, lo, a, lo, hi + 1 - lo);
            return;
        }

        int i = lo, j = mid + 1;

        for(int k = lo; k <= hi; k ++){
            if(i > mid)
                a[k] = aux[j ++];
            else if (j > hi)
                a[k] = aux[i ++];
            else
                a[k] = less(aux[j], aux[i]) ? aux[j ++] : aux[i ++];
        }
    }

    private void insertionSort(Comparable[] a, int lo, int hi) {
        for(int i = lo; i <= hi; i ++){
            for(int j = i; j > lo && less(a[j], a[j - 1]); j --)
                exchange(a, j, j - 1);
        }
    }

    public int getTHRESHOLD() {
        return THRESHOLD;
    }

    public void setTHRESHOLD(int THRESHOLD) {
        this.THRESHOLD = THRESHOLD;
    }
}
