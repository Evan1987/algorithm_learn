package Chap02_Sorting.Chap02_01;

import Chap02_Sorting.Sorting;

/**
 * 插入排序
 * 每次只让左半部分有序，且只交换相邻元素，与输入情况相关
 * 最差： ~n^2/2次比较，~n^2/2次交换
 * 最好： n-1次比较，0次交换
 * */
public class InsertionSort extends Sorting {
    @Override
    public void sort(Comparable[] a) {
        int N = a.length;
        for(int i = 1; i < N; i ++){
            for(int j = i; j > 0 && Sorting.less(a[j], a[j - 1]); j --)
                Sorting.exchange(a, j, j - 1);
        }
    }

    public static void main(String[] args) {
        String[] arr = {"s", "o", "r", "t", "e", "x", "a", "m", "p", "l", "e"};
        InsertionSort insertionSort = new InsertionSort();
        test(insertionSort, arr);
    }
}
