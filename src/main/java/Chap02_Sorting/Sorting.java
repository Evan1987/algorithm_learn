package Chap02_Sorting;

import utils.annotations.WatchTime;

public abstract class Sorting {

    @WatchTime
    public abstract void sort(Comparable[] a);

    public static boolean less(Comparable v, Comparable w){
        return v.compareTo(w) < 0;
    }

    public static void exchange(Comparable[] a, int i, int j){
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    public static void show(Comparable[] a){
        for (Comparable x : a) System.out.print(x + " ");
        System.out.println();
    }

    public static boolean isSorted(Comparable[] a){
        for(int i = 1; i < a.length; i ++)
            if(less(a[i], a[i - 1])) return false;
        return true;
    }

    public static void test(Sorting sorting, Comparable[] a){
        sorting.sort(a);
        assert isSorted(a): "The array is not sorted";
        show(a);
    }
}
