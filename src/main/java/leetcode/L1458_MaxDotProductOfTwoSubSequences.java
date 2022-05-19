package leetcode;


/**
 * @author : zhaochengming
 * @date : 2022/5/19 12:27
 * @description : https://leetcode.cn/problems/max-dot-product-of-two-subsequences/
 */
public class L1458_MaxDotProductOfTwoSubSequences {
    static class Solution {
        public int maxDotProduct(int[] nums1, int[] nums2) {
            int m = nums1.length, n = nums2.length;

            // dp[i][j] 代表 nums1[...i] 与 nums2[...j] 的最大内积
            int[][] dp = new int[m][n];

            for (int i = 0; i < m; i ++) {
                for (int j = 0; j < n; j ++) {
                    int product = nums1[i] * nums2[j];
                    dp[i][j] = product;
                    if (i > 0) {
                        dp[i][j] = Math.max(dp[i - 1][j], dp[i][j]);
                    }
                    if (j > 0) {
                        dp[i][j] = Math.max(dp[i][j - 1], dp[i][j]);
                    }

                    if (i > 0 && j > 0) {
                        dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - 1] + product);
                    }
                }
            }

            return dp[m - 1][n - 1];
        }
    }
}
