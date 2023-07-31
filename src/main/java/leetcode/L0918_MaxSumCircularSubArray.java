package leetcode;

/**
 * @author : zhaochengming
 * @date : 2023/7/31 14:13
 * @description : https://leetcode.cn/problems/maximum-sum-circular-subarray/
 * @see L0053_MaxSumSubArray
 * 环形子数组的最大和
 */
public class L0918_MaxSumCircularSubArray {
    static class Solution {
        // 动态规划
        // 考虑环形数组，可以对普通数组取反，即Sum(j - n - i) = Sum(0, n) - Sum(i, j)
        // dp[i] 依然表示以i结尾的子数组
        // 对应的最大和 dp[i] = max(dp[i - 1] + nums[i], nums[i])
        // 对应的最小和 dp[i] = min(dp[i - 1] + nums[i], nums[i])
        public int maxSubarraySumCircular(int[] nums) {
            int maxRes = Integer.MIN_VALUE, minRes = Integer.MAX_VALUE, sum = 0;
            int dpMax = 0, dpMin = 0;
            for (int num: nums) {
                sum += num;
                dpMax = Math.max(dpMax + num, num);
                dpMin = Math.min(dpMin + num, num);

                maxRes = Math.max(maxRes, dpMax);
                minRes = Math.min(minRes, dpMin);
            }

            // 最大子数组都为负，说明最小子数组可以直接取整个数组 -> 取反时将为空数组
            if (maxRes < 0) return maxRes;
            return Math.max(sum - minRes, maxRes);
        }
    }
}
