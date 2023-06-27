package leetcode.ubiquant;


import java.util.LinkedList;
import java.util.Queue;

/**
 * @author : zhaochengming
 * @date : 2023/6/27 14:31
 * @description : https://leetcode.cn/contest/ubiquant2022/problems/3PHTGp/
 * 池塘计数
 */
public class Q002_LakeCount {
    static class Solution {

        static class Pair {
            int i;
            int j;

            Pair(int i, int j) {
                this.i = i;
                this.j = j;
            }
        }

        public int lakeCount(String[] field) {
            int m = field.length;
            int n = field[0].length();
            boolean[][] visit = new boolean[m][n];
            int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};

            int ans = 0;
            for (int i = 0; i < m; i ++) {
                for (int j = 0; j < n; j ++) {
                    if (!visit[i][j] && field[i].charAt(j) == 'W') {
                        ans ++;
                        visit[i][j] = true;
                        Queue<Pair> q = new LinkedList<>();
                        q.offer(new Pair(i, j));

                        while (!q.isEmpty()) {
                            Pair pair = q.poll();
                            for (int[] direction: directions) {
                                int row = pair.i + direction[0];
                                int col = pair.j + direction[1];
                                if (row < m && row >= 0 && col < n && col >= 0 && !visit[row][col] && field[row].charAt(col) == 'W') {
                                    visit[row][col] = true;
                                    q.offer(new Pair(row, col));
                                }
                            }
                        }
                    }
                }
            }
            return ans;

        }
    }
}
