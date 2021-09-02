package algo4.chap01_fundamentals.Chap01_01;

import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;
import java.util.Collections;


public class Ex22 {

    private static int rank(int key, int[] a) {
        return rank(key, a, 0, a.length - 1, 0);
    }

    private static int rank(int key, int[] a, int lo, int hi, int depth){
        String info = String.join("", Collections.nCopies(depth, " "));
        info += "lo=" + lo + "\t" + "hi=" + hi + "\t" + "depth=" + depth;
        System.out.println(info);
        if(lo > hi) throw new ValueException(String.format("key `%d` not found", key));
        int mid = lo + (hi - lo) / 2;
        if(key < a[mid]) return rank(key, a, lo, mid - 1, depth + 1);
        if(key > a[mid]) return rank(key, a, mid + 1, hi, depth + 1);
        return mid;
    }

    public static void main(String[] args){
        int[] a = {1, 2, 3, 4, 6, 7, 8};
        int key = 5;
        System.out.println(rank(key, a));
    }

}
