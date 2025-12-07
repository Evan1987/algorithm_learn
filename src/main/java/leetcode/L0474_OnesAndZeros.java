package leetcode;

/*
 *
 * @author : zhaochengming.zcm
 * @date : 2025/12/7 23:26
 * https://leetcode.cn/problems/ones-and-zeroes/
 */
public class L0474_OnesAndZeros {
    static class Solution {
        public int findMaxForm(String[] strs, int m, int n) {
            int l = strs.length;
            // dp[i][j][k] 代表前i个字符串中，使用j个0和k个1的情况下允许的最大子集数量
            int[][][] dp = new int[l + 1][m + 1][n + 1];
            for (int i = 1; i <= l; i ++) {
                int[] counts = getCounts(strs[i - 1]);
                int zeros = counts[0], ones = counts[1];
                for (int j = 0; j <= m; j ++) {
                    for (int k = 0; k <= n; k ++) {
                        if (j < zeros || k < ones) {
                            // 无法装下，只能放弃 i号字符串
                            dp[i][j][k] = dp[i - 1][j][k];
                        } else {
                            // 选择不装，或者装下
                            dp[i][j][k] = Math.max(dp[i - 1][j][k], dp[i - 1][j - zeros][k - ones] + 1);
                        }
                    }
                }
            }
            return dp[l][m][n];
        }

        private int[] getCounts(String s) {
            int[] counts = new int[2];
            for (char c: s.toCharArray()) {
                if (c == '0') counts[0] ++;
                else counts[1] ++;
            }
            return counts;
        }
    }
}
