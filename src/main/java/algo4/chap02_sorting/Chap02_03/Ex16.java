package algo4.chap02_sorting.Chap02_03;

/**
 * @author Evan
 * @date 2020/2/29 13:02
 * 最佳数组
 */
public class Ex16 {
    public static void main(String[] args) {
        int[] x = best(10);
        for(int s: x) System.out.print(s + " ");
    }

    private static int[] best(int n){
        int[] a = new int[n];
        for(int i = 0; i < n; i ++)
            a[i] = i;
        best(a, 0, n - 1);
        return a;
    }

    /**
     * 每次让 lo作为 mid
     * */
    private static void best(int[] a, int lo, int hi){
        if(lo >= hi) return;
        int mid = lo + (hi - lo) / 2;
        best(a, lo, mid - 1);
        best(a, mid + 1, hi);
        exchange(a, lo, mid);
    }

    private static void exchange(int[] a, int i, int j){
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }
}


