package labuladong.chap02;

/**
 * @author : zhaochengming
 * @date : 2022/4/20 14:45
 * @description : 最大子数组，和最大的子数组，返回这个子数组的和
 */
public class MaxSubArray {

    public static int solve(int[] nums) {

        // dp[i]: 以nums[i]结尾的子数组最大和
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        for (int i = 1; i < dp.length; i ++) {
            dp[i] = dp[i - 1] < 0 ? nums[i] : dp[i - 1] + nums[i];
        }

        int res = Integer.MIN_VALUE;
        for (int x: dp) {
            res = Math.max(res, x);
        }

        return res;
    }

    // 状态压缩，solve方法中dp[i]只与dp[i-1]有关，可以将空间复杂度降下来
    public static int solve2(int[] nums) {
        int dp0 = nums[0];
        int dp1;
        int res = Integer.MIN_VALUE;
        for (int i = 1; i < nums.length; i ++) {
            dp1 = dp0 < 0 ? nums[i] : dp0 + nums[i];
            dp0 = dp1;
            res = Math.max(res, dp1);
        }
        return res;
    }


    public static void main(String[] args) {
        int[] nums = {-3, 1, 3, -1, 2, -4, 2};
        System.out.println(solve(nums));
        System.out.println(solve2(nums));
    }
}
