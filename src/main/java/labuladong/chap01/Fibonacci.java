package labuladong.chap01;

/**
 * @author : zhaochengming
 * @date : 2022/4/18 19:47
 * @description :
 */
public class Fibonacci {

    public static int solve(int N) {
        if (N == 0) return 0;
        if (N == 1 || N == 2) return 1;
        int prev = 1;
        int curr = 1;
        for (int i = 3; i <= N; i ++) {
            int sum = prev + curr;
            prev = curr;
            curr = sum;
        }
        return curr;
    }

    public static void main(String[] args) {
        System.out.println(solve(5));
    }
}
