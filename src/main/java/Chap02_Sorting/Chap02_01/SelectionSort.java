package Chap02_Sorting.Chap02_01;

import Chap02_Sorting.Sorting;

/**
 * 选择排序
 * 每次找出最小的元素，与前方交换
 * 与输入情况无关，n次交换，O(n^2 / 2)次比较
 * O(n^2)
 * */
public class SelectionSort extends Sorting {
    @Override
    public void sort(Comparable[] a) {
        int N = a.length;
        for(int i = 0; i < N; i ++){
            int min = i;
            for(int j = i + 1; j < N; j ++){
                if(Sorting.less(a[j], a[min]))
                    min = j;
            }

            Sorting.exchange(a, i, min);
        }
    }

    public static void main(String[] args) {
        String[] arr = {"s", "o", "r", "t", "e", "x", "a", "m", "p", "l", "e"};
        SelectionSort selectionSort = new SelectionSort();
        test(selectionSort, arr);
    }
}
