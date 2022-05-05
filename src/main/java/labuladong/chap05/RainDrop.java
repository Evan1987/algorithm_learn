package labuladong.chap05;

import utils.annotations.WatchTime;

/**
 * @author : zhaochengming
 * @date : 2022/5/5 16:43
 * @description : 接雨水
 */
public class RainDrop {

    private static int findMax(int[] heights, int start, int end, int height) {
        int m = height;
        for (int i = start; i < end; i ++) {
            if (heights[i] > m) {
                m = heights[i];
            }
        }

        return m;
    }

    @WatchTime(methodDesc = "brute force")
    public static int solve1(int[] heights) {
        int n = heights.length;
        int d = 0;
        for (int i = 1; i < n - 1; i ++) {
            int curr = heights[i];
            int leftMax = findMax(heights, 0, i, curr);
            if (leftMax <= curr) {
                continue;
            }

            int rightMax = findMax(heights, i + 1, n, curr);
            if (rightMax <= curr) {
                continue;
            }

            d += Math.min(leftMax, rightMax) - curr;
        }

        return d;
    }

    @WatchTime(methodDesc = "dp")
    public static int solve2(int[] heights) {
        int n = heights.length;
        // 构建两个数组，缓存leftMax rightMax
        // leftMax[i]  代表heights[0...i]最高的高度
        // rightMax[i] 代表heights[i....n-1]最高的高度
        int[] leftMax = new int[n];
        leftMax[0] = heights[0];
        for (int i = 1; i < n; i ++) {
            leftMax[i] = Math.max(heights[i], leftMax[i - 1]);
        }
        int[] rightMax = new int[n];
        rightMax[n - 1] = heights[n - 1];
        for (int i = n - 2; i >= 0; i --) {
            rightMax[i] = Math.max(heights[i], rightMax[i + 1]);
        }

        int d = 0;
        for (int i = 1; i < n - 1; i ++) {
//            int max = Math.min(leftMax[i - 1], rightMax[i + 1]);
//            d += Math.max(max - heights[i], 0);
            d += Math.min(leftMax[i], rightMax[i]) - heights[i];
        }

        return d;
    }

    @WatchTime(methodDesc = "dual pointers")
    public static int solve3(int[] heights) {
        int n = heights.length;
        int left = 0, right = n - 1;
        int leftMax = heights[0], rightMax = heights[n - 1];

        int d = 0;
        while (left < right) {
            leftMax = Math.max(leftMax, heights[left]);
            rightMax = Math.max(rightMax, heights[right]);
            if (leftMax < rightMax) {
                d += leftMax - heights[left];
                left ++;
            } else {
                d += rightMax - heights[right];
                right --;
            }
        }

        return d;
    }

    public static void main(String[] args) {
        int[] heights = {0, 1, 0, 2, 1, 0, 1, 3, 1, 1, 2, 1};
        System.out.println(solve1(heights));
        System.out.println(solve2(heights));
        System.out.println(solve3(heights));
    }
}
