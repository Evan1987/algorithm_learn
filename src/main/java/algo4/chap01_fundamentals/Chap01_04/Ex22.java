package algo4.chap01_fundamentals.Chap01_04;

import utils.FibonacciSearch;

/**
 * 无重复值的二分查找
 * */
public class Ex22 {
    public static void main(String[] args) {
        FibonacciSearch fibonacciSearch = new FibonacciSearch(20);
        int[] a = {1, 2, 2, 3, 4, 5, 5, 5, 5, 6, 9, 18, 29};
        System.out.println(fibonacciSearch.rank(a, 5, true));  // 7
        System.out.println(fibonacciSearch.rank(a, 18, true));  // 11
        System.out.println(fibonacciSearch.rank(a, 22, true));  // -1
    }

}
