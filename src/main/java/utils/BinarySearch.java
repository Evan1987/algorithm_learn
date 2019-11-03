package utils;

public class BinarySearch {
    public static int rank(int key, int[] a){
        return rank(key, a, 0, a.length - 1);
    }

    public static int rank(int key, int[] a, int lo, int hi){
        if(lo > hi) return -1;
        int mid = lo + (hi - lo) / 2;
        if(key < a[mid]) return rank(key, a, lo, mid - 1);
        if(key > a[mid]) return rank(key, a, mid + 1, hi);
        return mid;
    }

    /**
     * Added by Ex 1.4.10
     * */
    public static int rankMin(int key, int[] a){
        int index = -1;
        int lo = 0;
        int hi = a.length - 1;
        while(lo <= hi){
            int mid = lo + (hi - lo) / 2;
            if(key < a[mid]){
                hi = mid - 1;
            }else if(key > a[mid]){
                lo = mid + 1;
            }else{
                index = mid;
                hi = mid - 1;
            }
        }
        return index;
    }

    /**
     * Added by Ex 1.4.10
     * */
    public static int rankMax(int key, int[] a){
        int index = -1;
        int lo = 0;
        int hi = a.length - 1;
        while(lo <= hi){
            int mid = lo + (hi - lo) / 2;
            if(key < a[mid]){
                hi = mid - 1;
            }else if(key > a[mid]){
                lo = mid + 1;
            }else{
                index = mid;
                lo = mid + 1;
            }
        }
        return index;
    }

    public static void main(String[] args) {
        int[] a = {1, 2, 2, 3, 4, 5, 5, 5, 5, 6};  // 5 in 5, 6, 7, 8
        System.out.println(rank(5, a));  // 7
        System.out.println(rankMin(5, a));  // 5
        System.out.println(rankMax(5, a));  // 8
    }
}
