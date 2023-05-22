package leetcode;

import java.util.Arrays;

/**
 * @author : zhaochengming
 * @date : 2023/5/22 16:40
 * @description : https://leetcode.cn/problems/delete-greatest-value-in-each-row/
 * 删除矩阵中每行最大值
 */
public class L2500_DeleteGreatestValueInEachRow {
    static class Solution {
        public int deleteGreatestValue(int[][] grid) {
            int n = grid.length;
            int m = grid[0].length;

            for (int[] row : grid) {
                Arrays.sort(row);
            }

            int ans = 0;
            for (int j = m - 1; j >= 0; j --) {
                int x = 0;
                for (int i = 0; i < n; i ++) {
                    x = Math.max(x, grid[i][j]);
                }
                ans += x;
            }

            return ans;

        }
    }
}
