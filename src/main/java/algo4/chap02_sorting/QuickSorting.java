package algo4.chap02_sorting;


import utils.ArrayUtil;
import java.util.*;

/**
 * @author Evan
 * @date 2020/2/25 19:58
 */

public abstract class QuickSorting extends Sorting {
    public void sort(Comparable[] a){
        Random rnd = new Random();
        ArrayUtil.shuffle(a, rnd);
        sort(a, 0, a.length - 1);
    }

    public void sort(Comparable[] a, int lo, int hi){
        if(hi <= lo) return;
        int j = partition(a, lo, hi);
        sort(a, lo, j - 1);  // a[lo... j-1]排序
        sort(a, j + 1, hi);  // a[j+1 ...hi]排序
    }

    public abstract int partition(Comparable[] a, int lo, int hi);

}
