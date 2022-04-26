package labuladong.chap02;

import utils.annotations.WatchTime;

/**
 * @author : zhaochengming
 * @date : 2022/4/24 00:33
 * @description : 戳气球
 */
public class PokeBalloon {

    private static int res = 0;

    private static int[] addVirtualBalloon(int[] nums) {
        int n = nums.length;
        // 加入两个虚拟气球，解决边界问题
        int[] balloons = new int[n + 2];
        System.arraycopy(nums, 0, balloons, 1, n);
        balloons[0] = 1;
        balloons[n + 1] = 1;
        return balloons;
    }

    private static int[] deleteElement(int[] nums, int i) {
        int[] newNums = new int[nums.length - 1];
        System.arraycopy(nums, 0, newNums, 0, i);
        if (i < nums.length - 1) {
            System.arraycopy(nums, i + 1, newNums, i, nums.length - i - 1);
        }
        return newNums;
    }

    private static void backTrack(int[] balloons, int score) {
        // 只剩下边界
        if (balloons.length == 2) {
            res = Math.max(res, score);
            return;
        }

        for (int i = 1; i <= balloons.length - 2; i ++) {
            int point = balloons[i - 1] * balloons[i] * balloons[i + 1];
            backTrack(deleteElement(balloons, i), score + point);
        }
    }

    // 回溯方法
    @WatchTime(methodDesc = "Back track")
    public static int solve1(int[] nums) {
        int[] balloons = addVirtualBalloon(nums);
        backTrack(balloons, 0);
        return res;
    }

    // 动态规划方法
    @WatchTime(methodDesc = "DP")
    public static int solve2(int[] nums) {
        int n = nums.length;
        int[] balloons = addVirtualBalloon(nums);

        // dp[i][j] 代表戳破 (i...j)所有气球得到的最高分数，开区间
        int[][] dp = new int[n + 2][n + 2];
        // base case i >= j 均为 0， java数组初始化已经满足

        // 倒着遍历
        for (int i = n; i >= 0; i --) {
            for (int j = i + 1; j < n + 2; j ++) {
                // k代表最后被戳破的气球
                for (int k = i + 1; k < j; k ++) {
                    dp[i][j] = Math.max(dp[i][j], dp[i][k] + dp[k][j] + balloons[i] * balloons[k] * balloons[j]);
                }
            }
        }

        return dp[0][n + 1];
    }

    public static void main(String[] args) {
        int[] nums = {3, 1, 5, 8};
        System.out.println(solve1(nums));
        System.out.println(solve2(nums));
    }
}
