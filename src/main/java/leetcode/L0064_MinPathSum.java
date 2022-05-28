package leetcode;

import java.util.*;

/**
 * @author : zhaochengming
 * @date : 2022/5/28 23:58
 * @description : 网格的最小路径和
 * https://leetcode.cn/problems/minimum-path-sum/
 */
public class L0064_MinPathSum {
    static class Solution {

        // dp
        public int minPathSum(int[][] grid) {
            int m = grid.length, n = grid[0].length;
            int[][] dp = new int[m][n];
            dp[0][0] = grid[0][0];
            for (int i = 1; i < m; i ++) {
                dp[i][0] = dp[i - 1][0] + grid[i][0];
            }

            for (int j = 1; j < n; j ++) {
                dp[0][j] = dp[0][j - 1] + grid[0][j];
            }

            for (int i = 1; i < m; i ++) {
                for (int j = 1; j < n; j ++) {
                    dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + grid[i][j];
                }
            }

            return dp[m - 1][n - 1];

        }

        // Dijkstra
        public int minPathSum2(int[][] grid) {

            int m = grid.length, n = grid[0].length;

            // dist越小，坐标越大排前面
            PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> {
                if (o1[0] == o2[0]) {
                    return o2[1] + o2[2] - o1[1] - o1[2];
                }

                return o1[0] - o2[0];
            });

            pq.offer(new int[]{grid[0][0], 0, 0});
            Set<int[]> visited = new HashSet<>();
            int ans;
            while (true) {
                int[] info = pq.poll();
                int dist = info[0], i = info[1], j = info[2];
                if (!visited.add(new int[]{i, j})) {
                    continue;
                }

                if (i == m - 1 && j == n - 1) {
                    ans = dist;
                    break;
                }

                if (i + 1 < m) {
                    pq.offer(new int[]{dist + grid[i + 1][j], i + 1, j});
                }

                if (j + 1 < n) {
                    pq.offer(new int[]{dist + grid[i][j + 1], i, j + 1});
                }
            }

            return ans;
        }
    }
}
