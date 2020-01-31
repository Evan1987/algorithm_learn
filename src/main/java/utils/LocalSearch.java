package utils;

import java.util.Optional;


/**
 * Added by 1.4.18
 * */
public class LocalSearch {

    private static Optional<Integer> localSearch(int[] a, int lo, int hi, boolean minimum){
        if(hi - lo < 3) return Optional.empty();
        int mid = lo + (hi - lo) / 2;
        if(minimum && a[mid - 1] >= a[mid] && a[mid] <= a[mid + 1]) return Optional.of(mid);
        if((!minimum) && a[mid - 1] <= a[mid] && a[mid] >= a[mid + 1]) return Optional.of(mid);
        Optional<Integer> left = localSearch(a, lo, mid, minimum);  // find in left part
        if(left.isPresent()) return left;  // return left if there's a local target
        return localSearch(a, mid, hi, minimum);
    }

    public static int localMinimum(int[] a){
        Optional<Integer> index = localSearch(a, 0, a.length, true);
        return index.orElse(-1);
    }

    public static int localMaximum(int[] a){
        Optional<Integer> index = localSearch(a, 0, a.length, false);
        return index.orElse(-1);
    }
}
