package Chap02_Sorting;

import org.apache.commons.lang3.time.StopWatch;


public abstract class Sorting {

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
        StopWatch watcher = new StopWatch();
        watcher.start();
        sorting.sort(a);
        watcher.stop();
        assert isSorted(a): "The array is not sorted";
        show(a);
        System.out.printf("Sorting Test Cost Time: %.4f ms\n", watcher.getNanoTime() / 1000000.0);
    }
}
