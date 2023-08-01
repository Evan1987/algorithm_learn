package leetcode;

/**
 * @author : zhaochengming
 * @date : 2023/8/1 18:29
 * @description : https://leetcode.cn/problems/check-if-it-is-a-straight-line/
 * 判断是否点在一条直线上
 */
public class L1232_CheckIfStraightLine {
    static class Solution {
        public boolean checkStraightLine(int[][] coordinates) {
            int n = coordinates.length;
            if (n < 3) return true;

            int x0 = coordinates[0][0], y0 = coordinates[0][1];

            int deltaX = coordinates[1][0] - x0;
            int deltaY = coordinates[1][1] - y0;

            for (int i = 2; i < n; i ++) {
                int x = coordinates[i][0], y = coordinates[i][1];
                if (deltaX * (y - y0) != deltaY * (x - x0)) return false;
            }

            return true;
        }
    }
}
