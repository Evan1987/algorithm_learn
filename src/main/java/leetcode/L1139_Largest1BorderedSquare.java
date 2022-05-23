package leetcode;


/**
 * @author : zhaochengming
 * @date : 2022/5/23 17:40
 * @description : https://leetcode.cn/problems/largest-1-bordered-square/
 */
public class L1139_Largest1BorderedSquare {
    static class Solution {
        public int largest1BorderedSquare(int[][] grid) {
            int m = grid.length, n = grid[0].length;

            // 前缀和，便于计算 同行、同列1 的数量
            int[][] rowCounts = new int[m][n + 1];
            for (int i = 0; i < m; i ++) {
                for (int j = 1; j <= n; j ++) {
                    rowCounts[i][j] = rowCounts[i][j - 1] + grid[i][j - 1];
                }
            }


            int[][] colCounts = new int[m + 1][n];
            for (int j = 0; j < n; j ++) {
                for (int i = 1; i <= m; i ++) {
                    colCounts[i][j] = colCounts[i - 1][j] + grid[i - 1][j];
                }
            }

            int size = Math.min(m, n);

            for (int sz = size; sz >= 1; sz --) {
                for (int i = 0; i + sz - 1 < m; i ++) {
                    for (int j = 0; j + sz - 1 < n; j ++) {
                        if (isSquare(rowCounts, colCounts, i, j, sz)) return sz * sz;
                    }
                }
            }

            return 0;

        }

        // i, j 左上角
        private static boolean isSquare(int[][] rowCounts, int[][] colCounts, int startRow, int startCol, int sz) {
            int endRow = startRow + sz - 1, endCol = startCol + sz - 1;
            return rowCounts[startRow][endCol + 1] - rowCounts[startRow][startCol] == sz
                    && rowCounts[endRow][endCol + 1] - rowCounts[endRow][startCol] == sz
                    && colCounts[endRow + 1][startCol] - colCounts[startRow][startCol] == sz
                    && colCounts[endRow + 1][endCol] - colCounts[startRow][endCol] == sz;
        }
    }

    public static void main(String[] args) {
        int[][] grid = {{1, 1, 1}, {1, 0, 1}, {1, 1, 1}};
        Solution sol = new Solution();
        System.out.println(sol.largest1BorderedSquare(grid));
    }
}
