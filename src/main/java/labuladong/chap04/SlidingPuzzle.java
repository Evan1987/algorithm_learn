package labuladong.chap04;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * @author : zhaochengming
 * @date : 2022/5/4 17:39
 * @description : 2 * 3数组转换问题，求最少步数
 */
public class SlidingPuzzle {

    // 2 * 3数组索引的相邻索引
    private static final int[][] neighbours =  {
            {1, 3}, {0, 2, 4}, {1, 5}, {0, 4}, {1, 3, 5}, {4, 2}
    };

    // 将2 * 3数组转化为1维数组
    private static int[] transform(int[][] board) {
        int[] res = new int[6];
        System.arraycopy(board[0], 0, res, 0, 3);
        System.arraycopy(board[1], 0, res, 3, 3);
        return res;
    }

    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    private static int findZero(int[] array) {
        for (int i = 0; i < array.length; i ++) {
            if (array[i] == 0) {
                return i;
            }
        }

        return -1;
    }

    public static int solve(int[][] start, int[][] target) {
        int[] start1d = transform(start);
        int[] target1d = transform(target);

        int step = 0;
        Set<String> visited = new HashSet<>();
        Queue<int[]> q = new LinkedList<>();
        q.add(start1d);

        while (!q.isEmpty()) {
            int sz = q.size();
            for (int i = 0; i < sz; i ++) {
                int[] candidate = q.poll();
                if (Arrays.equals(candidate, target1d)) {
                    return step;
                }

                int idx = findZero(candidate);
                for (int adj: neighbours[idx]) {
                    int[] newBoard = candidate.clone();
                    swap(newBoard, idx, adj);
                    String key = StringUtils.join(newBoard, "-");
                    if (visited.add(key)) {
                        q.offer(newBoard);
                    }
                }
            }
            step ++;
        }

        return -1;
    }

    public static void main(String[] args) {
        int[][] start = {{4, 1, 2}, {5, 0, 3}};
        int[][] target = {{1, 2, 3}, {4, 5, 0}};
        System.out.println(solve(start, target));

    }


}
