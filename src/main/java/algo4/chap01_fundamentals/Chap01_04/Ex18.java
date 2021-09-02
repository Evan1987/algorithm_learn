package algo4.chap01_fundamentals.Chap01_04;

import utils.LocalSearch;

public class Ex18 {
    public static void main(String[] args) {
        int[] a = {2, 3, 5, 8, 9, 11, 9, 10};
        System.out.println(LocalSearch.localMinimum(a));  // 6 no need for sort
    }
}
