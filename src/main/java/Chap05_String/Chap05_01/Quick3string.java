package Chap05_String.Chap05_01;

/**
 * @author zhaochengming
 * @date 2020/8/30 19:50
 */
public class Quick3string {
    private static int charAt(String s, int d){
        if(d < s.length()) return s.charAt(d);
        return -1;
    }

    private static void exchange(String[] a, int i, int j){
        String temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public static void sort(String[] a){
        sort(a, 0, a.length - 1, 0);
    }

    private static void sort(String[] a, int lo, int hi, int d){
        if(hi <= lo) return;
        int lt = lo, gt = hi;
        int v = charAt(a[lo], d);       // first char
        int i = lo + 1;

        // 3-way
        // [...lt) < v, [lt, gt] == v, (gt, hi] > v
        while(i < gt){
            int t = charAt(a[i], d);
            if          (t < v) exchange(a, lt ++, i ++);
            else if     (t > v) exchange(a, i, gt --);
            else        i ++;
        }

        sort(a, lo, lt - 1, d);
        if(v >= 0) sort(a, lt, gt, d + 1);
        sort(a, gt + 1, hi, d);
    }
}
