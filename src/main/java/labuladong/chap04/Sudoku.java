package labuladong.chap04;

/**
 * @author : zhaochengming
 * @date : 2022/5/4 16:44
 * @description : 解数独
 */
public class Sudoku {

    private static final char EMPTY = '.';

    // 逐列穷举，到头就转到下一行
    private static boolean backTrack(char[][] board, int i, int j) {
        if (j == 9) {
            return backTrack(board, i + 1, 0);
        }

        // 找到可行解
        if (i == 9) {
            return true;
        }

        if (board[i][j] != EMPTY) {
            return backTrack(board, i, j + 1);
        }

        for (char ch = '1'; ch <= '9'; ch ++) {
            if (!isValid(board, i, j, ch)) {
                continue;
            }

            board[i][j] = ch;
            // 找到可行解立刻返回
            if (backTrack(board, i, j + 1)) {
                return true;
            }
            board[i][j] = EMPTY;
        }

        return false;
    }

    // 判断 board[r][c] = ch是否合理
    private static boolean isValid(char[][] board, int r, int c, char ch) {
        // 同行、同列是否有冲突
        for (int i = 0; i < 9; i ++) {
            // 同列
            if (board[i][c] == ch) {
                return false;
            }
            // 同行
            if (board[r][i] == ch) {
                return false;
            }

            // 3 x 3方格内是否有冲突
            if (board[r / 3 * 3 + i / 3][c / 3 * 3 + i % 3] == ch) {
                return false;
            }
        }
        return true;
    }

    public static boolean solve(char[][] board) {
        return backTrack(board, 0, 0);
    }
}
