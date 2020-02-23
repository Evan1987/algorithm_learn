package Chap02_Sorting.Chap02_01;

import Chap02_Sorting.Sorting;
import utils.annotations.WatchTime;


public class BubbleSort extends Sorting {

    @WatchTime(methodDesc = "Bubble sort")
    @Override
    public void sort(Comparable[] a) {
        int N = a.length;
        for(int j = N; j > 0; j --){
            boolean flag = false;  // 如果内层循环没有发生交换，则说明已经有序，直接退出
            for(int i = 1; i < j; i ++){
                if(Sorting.less(a[i], a[i - 1])){
                    Sorting.exchange(a, i, i - 1);
                    if(!flag) flag = true;
                }
            }
            if(!flag) break;
        }
    }

    public static void main(String[] args) {
        String[] arr = {"s", "o", "r", "t", "e", "x", "a", "m", "p", "l", "e"};
        BubbleSort bubbleSort = new BubbleSort();
        test(bubbleSort, arr);
    }
}
