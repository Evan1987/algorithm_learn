package Chap01Fundamentals.Chap01_04;

public class Ex18 {
    private static int localSmallest(int[] a, int lo, int hi){
        if(hi - lo < 3) return 0;
        int mid = lo + (hi - lo) / 2;
        if(a[mid - 1] > a[mid] && a[mid] < a[mid + 1]) return mid;
        int left = localSmallest(a, lo, mid);  // find in left part
        if(left > 0) return left;
        int right = localSmallest(a, mid, hi);  // find in right part
        return Math.max(right, 0);
    }
    public static int localSmallest(int[] a){
        return localSmallest(a, 0, a.length);
    }

    public static void main(String[] args) {
        int[] a = {2, 3, 5, 8, 9, 11, 9, 10};
        System.out.println(localSmallest(a));  // 6
    }
}
