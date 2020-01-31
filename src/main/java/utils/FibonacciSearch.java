package utils;

import java.util.Arrays;

/**
 * F(n) - 1 = F(n-1) - 1 + F(n-2) - 1 + 1
 * The new arr will be like:
 *
 * |-------------------------------- F(n) - 1 elements --------------------------|
 * |----- F(n - 1) - 1 elements -----, `mid` element, -- F(n - 2) - 1 elements --|
 * */
public class FibonacciSearch {
    private int SIZE;
    private int[] fibonacciArr;

    public FibonacciSearch(int size){
        this.SIZE = size;
        this.fibonacciArr = createFibonacciArray(size);
    }

    public int rank(int[] a, int key, boolean presorted){
        if(!presorted) Arrays.sort(a);
        int n = a.length;

        // Find the minimum fibonacci number `x` and its index
        int k = findSuitableSize(n);
        int[] temp = Arrays.copyOf(a, this.fibonacciArr[k] - 1);
        for(int i = n; i < temp.length; i ++){
            temp[i] = a[n - 1];  // Copy the last element to fill the tail
        }

        int lo = 0;
        int hi = n - 1;
        while(lo <= hi){
            int mid = lo + this.fibonacciArr[k - 1] - 1;  // the mid is not included as boundary
            if(temp[mid] > key){  // on the left
                hi = mid - 1;
                k -= 1;
            }else if(temp[mid] < key){  // on the right
                lo = mid + 1;
                k -= 2;
            }else{
                return mid < n ? mid : n - 1;  // the mid may be on the filling area
            }
        }
        return -1;
    }

    /**
     * Given the length, find the minimum fibonacci number that bigger than it.
     * */
    private int findSuitableSize(int length){
        if(length < 1) return -1;
        int k = 0;

        // Must be bigger than length + 1(additional comparative point, the `mid`)
        while(this.fibonacciArr[k] - 1 < length){
            k ++;
        }
        return k;
    }

    private static int[] createFibonacciArray(int k){
        int[] a = new int[k];
        a[0] = 1;
        a[1] = 1;
        int i = 2;
        while(i < k){
            a[i] = a[i - 1] + a[i - 2];
            i ++;
        }
        return a;
    }
}
