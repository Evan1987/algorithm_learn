package algo4.chap02_sorting.Chap02_02;

import java.util.stream.IntStream;

/**
 * @author Evan
 * @date 2020/2/25 16:46
 * 间接排序，只给 perm数组，不排序
 */
public class Ex20 {
    public static void main(String[] args) {
        String[] arr = {"a", "b", "d", "c", "f", "e"};
        ArgSort argSort = new ArgSort();
        int[] perm = argSort.sort(arr);
        for(int x: perm)
            System.out.print(x + " ");
        System.out.println();
        display(arr, perm);
    }

    private static void display(Comparable[] a, int[] perm){
        int N = a.length;
        for(int i = 0; i < N; i ++)
            System.out.print(a[perm[i]] + " ");
    }
}

class ArgSort {

    public int[] sort(Comparable[] a){
        int N = a.length;
        int[] perm = IntStream.range(0, N).toArray();
        int[] aux = new int[N];
        sort(a, 0, N - 1, perm, aux);
        return perm;
    }

    private void sort(Comparable[] a, int lo, int hi, int[] perm, int[] aux){
        if(hi <= lo) return;
        int mid = lo + (hi - lo) / 2;
        sort(a, lo, mid, perm, aux);
        sort(a, mid + 1, hi, perm, aux);
        merge(a, lo, mid, hi, perm, aux);
    }

    private void merge(Comparable[] a, int lo, int mid, int hi, int[] perm, int[] aux){
        int i = lo, j = mid + 1;
        System.arraycopy(perm, lo, aux, lo, hi - lo + 1);

        for(int k = lo; k <= hi; k ++){
            if(i > mid)
                perm[k] = aux[j ++];
            else if(j > hi)
                perm[k] = aux[i ++];
            else
                perm[k] = less(a[aux[i]], a[aux[j]]) ? aux[i ++] : aux[j ++];
        }
    }

    private static boolean less(Comparable a, Comparable b){
        return a.compareTo(b) < 0;
    }
}
