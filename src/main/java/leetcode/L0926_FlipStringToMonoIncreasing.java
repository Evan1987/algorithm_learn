package leetcode;

/**
 * @author : zhaochengming
 * @date : 2023/7/31 13:07
 * @description : https://leetcode.cn/problems/flip-string-to-monotone-increasing/
 * 翻转字符串到单调递增
 */
public class L0926_FlipStringToMonoIncreasing {
    static class Solution {
        // 动态规划
        // dp[i][0]: i位置为0时保持单调递增的最小翻转数  dp[i][0] = dp[i - 1][0] + 1(s[i] == 1)
        // dp[i][1]: i位置为1时保持单调递增的最小翻转数  dp[i][1] = min(dp[i - 1][1], dp[i - 1][0]) + 1(s[i] == 0)
        public int minFlipsMonoIncr(String s) {
            int dp0 = 0, dp1 = 0, c1;  // c1 = 1(s[i] == 1)
            for (char c: s.toCharArray()) {
                c1 = c - '0';
                dp1 = Math.min(dp0, dp1) + (1 - c1);
                dp0 += c1;
            }

            return Math.min(dp0, dp1);
        }
    }
}
