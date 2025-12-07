package leetcode;

/*
 * @author : zhaochengming.zcm
 * @date : 2025/12/7 22:47
 * https://leetcode.cn/problems/edit-distance/description/
 */
public class L0072_EditDistance {
    static class Solution {
        private int min(int ... nums) {
            int ans = nums[0];
            for (int i = 1; i < nums.length; i ++) {
                if (nums[i] < ans) {
                    ans = nums[i];
                }
            }
            return ans;
        }

        public int minDistance(String word1, String word2) {
            int n = word1.length(), m = word2.length();
            if (n == 0) return m;
            if (m == 0) return n;

            // dp[i][j] 表示 s1[:i] 与 s2[:j] 编辑距离，首行和首列补空串计算
            int[][] dp = new int[n + 1][m + 1];
            // base case: 首列，对应s2为空串，则编辑距离随着s1增长而增长
            for (int i = 0; i <= n; i ++) {
                dp[i][0] = i;
            }
            // base case: 首行，对应s1为空串，则编辑距离随着s2增长而增长
            for (int j = 0; j <= m; j ++) {
                dp[0][j] = j;
            }

            for (int i = 1; i <= n ; i ++) {
                for (int j = 1; j <= m; j ++) {
                    if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                        // 当末尾字符相同时，s1[:i]、s[:j]编辑距离与 s[:(i-1)]、s[:(j-1)]相同
                        dp[i][j] = dp[i - 1][j - 1];
                    } else {
                        dp[i][j] = min(dp[i - 1][j], dp[i][j - 1], dp[i - 1][j - 1]) + 1;
                    }
                }
            }
            return dp[n][m];
        }
    }
}
