package leetcode;


/**
 * @author : zhaochengming
 * @date : 2022/5/16 22:43
 * @description : https://leetcode.cn/problems/matrix-block-sum/
 */
public class L1314_MatrixBlockSum {
    static class Solution {
        public int[][] matrixBlockSum(int[][] mat, int k) {
            int m = mat.length;
            int n = mat[0].length;

            // 前缀矩阵 P[i][j] 代表 以 （0,0）为左上角，（i-1，j-1)为右下角矩阵的和
            int[][] P = new int[m + 1][n + 1];
            for (int i = 1; i <= m; i ++) {
                for (int j = 1; j <= n; j ++) {
                    P[i][j] = P[i - 1][j] + P[i][j - 1] - P[i - 1][j - 1] + mat[i - 1][j - 1];
                }
            }

            int[][] ans = new int[m][n];
            for (int i = 0; i < m; i ++) {
                for (int j = 0; j < n; j ++) {
                    ans[i][j] = get(P, m, n, i + k + 1, j + k + 1) - get(P, m, n, i - k, j + k + 1)
                            - get(P, m, n, i + k + 1, j - k) + get(P, m, n, i - k, j - k);
                }
            }

            return ans;
        }

        private int get(int[][] P, int m, int n, int r, int c) {
            r = Math.max(Math.min(r, m), 0);
            c = Math.max(Math.min(c, n), 0);
            return P[r][c];
        }
    }
}
