package labuladong.chap01;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author : zhaochengming
 * @date : 2022/4/18 20:54
 * @description : N皇后，返回所有结果
 */
public class NQueen {

    public List<String[][]> res;
    public int n;

    public NQueen(int n) {
        this.n = n;
        this.res = new LinkedList<>();
    }

    private static String[][] generateEmptyBoard(int n) {
        String[][] board = new String[n][n];
        for (String[] strings : board) {
            Arrays.fill(strings, ".");
        }
        return board;
    }

    private static String[][] copyBoard(String[][] board) {
        int n = board.length;
        int m = board[0].length;
        String[][] newBoard = new String[n][m];
        for (int i = 0; i < n; i ++) {
            System.arraycopy(board[i], 0, newBoard[i], 0, m);
        }
        return newBoard;
    }

    private static boolean isValid(String[][] board, int row, int col) {

        int n = board.length;
        // 因填满是从上到下，因此仅检查上方即可

        // 同列
        for (int i = 0; i < row; i ++) {
            if (board[i][col].equals("Q"))
                return false;
        }

        // 左斜上方
        for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i --, j--) {
            if (board[i][j].equals("Q"))
                return false;
        }

        // 右斜上方
        for (int i = row - 1, j = col + 1; i >= 0 && j < n; i --, j ++) {
            if (board[i][j].equals("Q"))
                return false;
        }
        return true;
    }

    private void backTrack(String[][] board, int row) {
        int n = board.length;
        if (row >= n) {
            this.res.add(copyBoard(board));  // copy结果，不要影响继续搜索可行解
            return;
        }

        for (int col = 0; col < n; col ++) {
            if (!isValid(board, row, col)) {
                continue;
            }
            board[row][col] = "Q";
            backTrack(board,row + 1);  // 基于row行col列有Queen，会一直探到 row = n 结束

            board[row][col] = ".";  // 继续搜索row行的其它col列
        }

    }

    public static void solve(int n) {
        NQueen obj = new NQueen(n);
        String[][] board = generateEmptyBoard(n);
        obj.backTrack(board, 0);
        System.out.println("Total: " + obj.res.size());
        for (String[] row: obj.res.get(0)) {
            for (String s: row) {
                System.out.print(s + "  ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        NQueen.solve(8);
    }
}
