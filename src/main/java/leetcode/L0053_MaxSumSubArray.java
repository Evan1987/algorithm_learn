package leetcode;

/**
 * @author : zhaochengming
 * @date : 2023/7/31 14:16
 * @description : https://leetcode.cn/problems/maximum-subarray/description/
 * 最大子数组和
 */
public class L0053_MaxSumSubArray {
    static class Solution {

        // 动态规划
        // dp[i] 以i位置结尾的子数组最大和
        // dp[i] = max(dp[i - 1] + nums[i], nums[i])
        public int maxSubArray(int[] nums) {
            int ans = Integer.MIN_VALUE;
            int dp = 0;
            for (int num: nums) {
                dp = Math.max(dp + num, num);
                ans = Math.max(dp, ans);
            }
            return ans;
        }
    }
}
