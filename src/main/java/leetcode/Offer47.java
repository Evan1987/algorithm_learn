package leetcode;


/**
 * @author : zhaochengming
 * @date : 2022/5/17 22:03
 * @description : https://leetcode.cn/problems/li-wu-de-zui-da-jie-zhi-lcof/
 */
public class Offer47 {

    static class Solution {
        private int res = 0;

        // 回溯 超时限
        private void backTrack2(int[][] grid, int m, int n, int r, int c, int value) {
            value += grid[r][c];
            if (r == m - 1 && c == n - 1) {
                res = Math.max(res, value);
                return;
            }

            if (r < m - 1) {
                backTrack2(grid, m, n, r + 1, c, value);
            }

            if (c < n - 1) {
                backTrack2(grid, m, n, r, c + 1, value);
            }
        }

        public int maxValue2(int[][] grid) {
            int m = grid.length;
            int n = grid[0].length;
            backTrack2(grid, m, n, 0, 0, 0);
            return res;
        }


        // dp
        public int maxValue(int[][] grid) {
            int m = grid.length;
            int n = grid[0].length;

            // dp[i][j] 代表到达 grid[i][j] 最大价值
            int[][] dp = new int[m][n];
            // 首行首列
            dp[0][0] = grid[0][0];
            for (int j = 1; j < n; j ++) {
                dp[0][j] += dp[0][j - 1] + grid[0][j];
            }

            for (int i = 1; i < m; i ++) {
                dp[i][0] = dp[i - 1][0] + grid[i][0];
            }

            for (int i = 1; i < m; i ++) {
                for (int j = 1; j < n; j ++) {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]) + grid[i][j];
                }
            }

            return dp[m - 1][n - 1];
        }
    }



}
