package leetcode;


import java.util.Arrays;

/**
 * @author : zhaochengming
 * @date : 2022/5/20 11:01
 * @description : https://leetcode.cn/problems/perfect-squares/
 */
public class L0279_PerfectSquares {
    static class Solution {

        public int numSquares(int n) {
            // dp[i] 组成整数i的最少的数量
            int[] dp = new int[n + 1];
            Arrays.fill(dp, n);
            dp[1] = 1;
            for (int i = 2; i <= n; i ++) {
                int sqrt = (int) Math.sqrt(i);
                if (i == sqrt * sqrt) dp[i] = 1;
                for (int j = 1; j <= sqrt; j ++) {
                    dp[i] = Math.min(dp[i], dp[i - j * j] + 1);
                }
            }

            return dp[n];
        }
    }
}
