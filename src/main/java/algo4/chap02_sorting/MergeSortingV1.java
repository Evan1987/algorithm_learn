package algo4.chap02_sorting;

/**
 * @author Evan
 * @date 2020/2/23 17:18
 */

@Deprecated
public abstract class MergeSortingV1 extends Sorting{

    private static Comparable[] aux;  // helper array

    /**
     * merge a[lo..mid] and a[mid+1...hi], which are both already sorted
     * */
    public void merge(Comparable[] a, int lo, int mid, int hi){
        int i = lo, j = mid + 1;  // double index

        System.arraycopy(a, lo, aux, lo, hi + 1 - lo);

        for(int k = lo; k <= hi; k ++){
            if(i > mid) a[k] = aux[j ++];  // left part is empty, just move the right part
            else if (j > hi) a[k] = aux[i ++];  // right part is empty, just move the left part
            else if (less(aux[j], aux[i])) a[k] = aux[j ++];  // right one is less, move right
            else a[k] = aux[i ++];    // left one is less, move left
        }
    }

    public Comparable[] getAux(){return aux;}
    public void initAux(int N){aux = new Comparable[N];}

}
