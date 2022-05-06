package labuladong.chap05;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @author : zhaochengming
 * @date : 2022/5/6 12:45
 * @description : 区间调度， 最多有多少互不相交的区间
 */
public class IntervalScheduling {

    // 贪心，依次选结束时间最早的，去掉重叠的
    public static int solve(int[][] intervals) {
        if (intervals.length == 0) {
            return 0;
        }

        Arrays.sort(intervals, Comparator.comparingInt(o -> o[1]));
        int count = 1;
        int end = intervals[0][1];

        for (int[] interval: intervals) {
            if (interval[0] >= end) {
                count ++;
                end = interval[1];
            }
        }

        return count;
    }

    public static void main(String[] args) {
        int[][] intervals = {{1, 3}, {2, 4}, {3, 6}};
        System.out.println(solve(intervals));
    }
}
