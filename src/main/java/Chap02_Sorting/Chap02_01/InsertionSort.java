package Chap02_Sorting.Chap02_01;

import Chap02_Sorting.Sorting;
import utils.annotations.WatchTime;

/**
 * 插入排序
 * 每次只让左半部分有序，且只交换相邻元素，与输入情况相关
 * 最差： ~n^2/2次比较，~n^2/2次交换
 * 最好： n-1次比较，0次交换
 * */
public class InsertionSort extends Sorting {


    @WatchTime(methodDesc = "Insertion sort default")
    @Override
    public void sort(Comparable[] a) {
        int N = a.length;
        for(int i = 1; i < N; i ++){
            for(int j = i; j > 0 && Sorting.less(a[j], a[j - 1]); j --)
                Sorting.exchange(a, j, j - 1);
        }
    }

    /**
     * 规避边界测试， added by Ex2.1.24
     * */
    @WatchTime(methodDesc = "Insertion sort with guard")
    public void sort(Comparable[] a, boolean withGuard){
        if(!withGuard){
            sort(a);
            return;
        }

        int N = a.length;
        int minIndex = 0;
        for(int i = 1; i < N; i ++){
            if(Sorting.less(a[i], a[minIndex]))
                minIndex = i;
        }
        Sorting.exchange(a, 0, minIndex);  // 将最小值放置最左侧，作为边界哨兵
        for(int i = 1; i < N; i ++){
            for(int j = i; Sorting.less(a[j], a[j - 1]); j --)  // 少了边界条件 j > 0
                Sorting.exchange(a, j, j - 1);
        }
    }

    /**
     * 取消每次昂贵的exchange操作，改为数组右移 Added by Ex2.1.25
     * */
    @WatchTime(methodDesc = "Insertion sort without exchange")
    public void sortWithoutExchange(Comparable[] a){
        int N = a.length;
        for(int i = 1; i < N; i ++){
            Comparable t = a[i];
            int j;
            for(j = i; j > 0 && Sorting.less(t, a[j - 1]); j --)
                a[j] = a[j - 1];  // 右移
            a[j] = t;
        }
    }

    public static void main(String[] args) {
        String[] arr = {"s", "o", "r", "t", "e", "x", "a", "m", "p", "l", "e"};
        InsertionSort insertionSort = new InsertionSort();
        //test(insertionSort, arr);
        insertionSort.sortWithoutExchange(arr);
        for(String s: arr) System.out.print(s + " ");
    }
}
