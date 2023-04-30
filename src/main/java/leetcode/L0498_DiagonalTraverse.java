package leetcode;

/**
 * @author : zhaochengming
 * @date : 2023/4/30 21:40
 * @description : https://leetcode.cn/problems/diagonal-traverse/
 * 对角线遍历
 */
public class L0498_DiagonalTraverse {
    static class Solution {
        public int[] findDiagonalOrder(int[][] mat) {
            int m = mat.length;
            int n = mat[0].length;
            int[] ans = new int[m * n];
            int k = 0;
            for (int i = 0; i < m + n - 1; i ++) {
                // 右上↗遍历
                if (i % 2 == 0) {
                    // 找起点
                    int x = i < m ? i : m - 1;
                    int y = i - x;
                    while (x >= 0 && y < n) {
                        ans[k ++] = mat[x --][y ++];
                    }
                }
                // 左下↙遍历
                else {
                    // 找起点
                    int y = i < n ? i : n - 1;
                    int x = i - y;
                    while (x < m && y >= 0) {
                        ans[k ++] = mat[x ++][y --];
                    }
                }
            }

            return ans;
        }

    }
}
