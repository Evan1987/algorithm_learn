package labuladong.chap02;

import java.util.Arrays;

/**
 * @author : zhaochengming
 * @date : 2022/4/20 15:18
 * @description : 最长公共子序列
 */
public class LCS {

    public static int solve(String s1, String s2) {

        int m = s1.length(), n = s2.length();

        // dp[i][j] 代表 s1[:i]与s2[:j]的LCS长度，首行和首列补空串计算
        int[][] dp = new int[m + 1][n + 1];

        // base case: 0 行和 0 列都填为0
        Arrays.fill(dp[0], 0);
        for (int i = 0; i < dp.length; i ++) {
            dp[i][0] = 0;
        }

        for (int i = 1; i <= m; i ++) {
            for (int j = 1; j <= n; j ++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        return dp[m][n];
    }

    public static void main(String[] args) {
        String s1 = "babcde";
        String s2 = "acze";
        System.out.println(solve(s1, s2));
    }
}
