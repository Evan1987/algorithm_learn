package leetcode;

import java.util.*;

/**
 * @author : zhaochengming
 * @date : 2022/5/25 17:30
 * @description : https://leetcode.cn/problems/eight-queens-lcci/
 */
public class MST0812_EightQueens {
    static class Solution {

        private List<List<String>> boards;

        private void backTrack(char[][] board, int n, int row) {
            if (row == n) {
                boards.add(convert(board));
                return;
            }

            for (int j = 0; j < n; j ++) {
                if (isValid(board, row, j)) {
                    board[row][j] = 'Q';
                    backTrack(board, n, row + 1);
                    board[row][j] = '.';
                }
            }
        }

        // 在 row、col位置放置Q是否ok
        private static boolean isValid(char[][] board, int row, int col) {

            int n = board.length;
            for (int i = row - 1; i >= 0; i --) {
                // 判断同列
                if (board[i][col] == 'Q') return false;

                if ((col + row - i < n && board[i][col + row - i] == 'Q') ||
                        (col - row + i >= 0 && board[i][col - row + i] == 'Q')) return false;
            }
            return true;
        }

        private static List<String> convert(char[][] board) {
            List<String> res = new ArrayList<>();
            for (char[] row: board) {
                res.add(new String(row));
            }
            return res;
        }

        public List<List<String>> solveNQueens(int n) {
            boards = new LinkedList<>();
            char[][] board = new char[n][n];
            for (int i = 0; i < n; i ++) {
                Arrays.fill(board[i], '.');
            }

            backTrack(board, n, 0);
            return boards;
        }
    }
}
