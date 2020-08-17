package Chap05_String.Chap05_01;

/**
 * @author zhaochengming
 * @date 2020/8/17 10:14
 */
public class MSD {
    private static final int R = 256;       // extend ASCII alphabet size
    private static final int CUT_OFF = 15;  // the threshold to judge if using insertion sort

    public static void sort(String[] a){
        int n = a.length;
        String[] aux = new String[n];
        sort(a, 0, n - 1, 0, aux);
    }

    /**
     * An override `charAt` method for d out of range of length
     * */
    private static int charAt(String s, int d){
        assert d >= 0;
        if(d >= s.length()) return -1;
        return s.charAt(d);
    }

    /**
     * Sort from a[lo] to a[hi] (all included), starting at the `d`th character for each element.
     * @param a: The original array to sort
     * @param lo: The lowest index to sort
     * @param hi: The highest index to sort
     * @param aux: The helper array with same length as `a`;
     * */
    private static void sort(String[] a, int lo, int hi, int d, String[] aux) {
        if (hi - lo <= CUT_OFF) {
            insertionSort(a, lo, hi, d);
            return;
        }

        // Count the num for each group
        int[] count = new int[R + 2];
        for (int i = lo; i <= hi; i++)
            count[charAt(a[i], d) + 2]++;

        // Get the start index for each group
        for (int i = 0; i < R + 1; i++)
            count[i + 1] += count[i];

        // Distribute
        for (int i = lo; i <= hi; i++)
            aux[count[charAt(a[i], d) + 1]++] = a[i];

        // Copy back
        System.arraycopy(aux, 0, a, lo, hi - lo + 1);

        // Recursively sort for each character
        for(int r = 0; r < R; r ++)
            sort(a, lo + count[r], lo + count[r + 1] - 1, d + 1, aux);
    }

    /**
     * The insertion sort for short length array
     * */
    private static void insertionSort(String[] a, int lo, int hi, int d){
        for(int i = lo; i <= hi; i ++)
            for(int j = i; j > lo && less(a[j], a[j - 1], d); j --)
                exchange(a, j, j - 1);
    }

    /**
     * Exchange the element between `i`th and `j`th of array `a`.
     * */
    private static void exchange(String[] a, int i, int j){
        String temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    /**
     * Compare string `v` and `w` from `d`th character, return if `v` is small than `w`.
     * */
    private static boolean less(String v, String w, int d){
        for(int i = d; i < Math.min(v.length(), w.length()); i ++){
            if(v.charAt(i) == w.charAt(i)) continue;
            return v.charAt(i) < w.charAt(i);
        }
        return v.length() < w.length();
    }

    public static void main(String[] args) {
        String[] licenses = {
                "4PGC938", "21YE230", "3CIO720", "1ICK750", "1OHV845",
                "4JZY524", "1ICK750", "3CIO720", "1OHV845", "1OHV845",
                "2RLA629", "2RLA629", "3ATW723"};
        MSD.sort(licenses);
        for(String license: licenses)
            System.out.println(license);
    }


}
