package Chap02_Sorting;

/**
 * Without static aux arr to improve safety
 * @author Evan
 * @date 2020/2/24 11:45
 * added by Ex 09
 */
public abstract class MergeSortingV2 extends Sorting {

    public void merge(Comparable[] a, int lo, int mid, int hi, Comparable[] aux){
        int i = lo, j = mid + 1;
        System.arraycopy(a, lo, aux, lo, hi - lo + 1);

        for(int k = lo; k <= hi; k ++){
            if(i > mid)
                a[k] = aux[j ++];
            else if (j > hi)
                a[k] = aux[i ++];
            else
                a[k] = less(aux[j], aux[i]) ? aux[j ++] : aux[i ++];
        }
    }
}
