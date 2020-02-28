package Chap02_Sorting.Chap02_03;

import Chap02_Sorting.Sorting;

/**
 * @author Evan
 * @date 2020/2/28 10:16
 * 只有两种主键值的数组排序，参考 QuickSort3Way
 */
public class Ex05 {
    public static void main(String[] args) {
        Quick2UniqueElements sorter = new Quick2UniqueElements();
        String[] a = {"a", "b", "a", "b", "a", "a", "b", "b"};
        Sorting.test(sorter, a);
    }
}


class Quick2UniqueElements extends Sorting {

    @Override
    public void sort(Comparable[] a) {
        int N = a.length;
        int lt = 0, i = 1, gt = N - 1;
        Comparable v = a[0];
        while(i <= gt){
            int cmp = a[i].compareTo(v);
            if(cmp < 0) exchange(a, lt ++, i ++);
            else if(cmp > 0) exchange(a, i, gt --);
            else i ++;
        }
    }
}