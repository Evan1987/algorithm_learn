package Chap02_Sorting.Chap02_01;

import Chap02_Sorting.Sorting;

/**
 * 希尔排序
 * 插入排序就是希尔排序的一个特例，即 h=1的希尔排序
 * */
public class ShellSort extends Sorting{
    private int V;

    ShellSort(){
        this.V = 3;
    }

    ShellSort(int v){
        this.V = v;
    }

    @Override
    public void sort(Comparable[] a) {
        int N = a.length;
        int h = 1;
        while(h < N / this.V) h = this.V * h + 1;  // 1, 4, 13, 40, ... 递增序列  v * h + 1，v默认为3
        while(h >= 1){
            for(int i = h; i < N; i ++){
                for(int j = i; j >= h && Sorting.less(a[j], a[j - h]); j -= h)
                    Sorting.exchange(a, j, j - h);  // 每间隔 h 有序，保证可以移动到更远的位置
            }
            h /= this.V;  // 每次缩减 h，由于之前已经增加了尾数1，所以一定会缩减到1
        }
    }

    public static void main(String[] args) {
        String[] arr = {"s", "o", "r", "t", "e", "x", "a", "m", "p", "l", "e"};
        ShellSort shellSort = new ShellSort(4);
        test(shellSort, arr);
    }
}
