package leetcode;

/**
 * @author : zhaochengming
 * @date : 2023/5/6 10:21
 * @description : https://leetcode.cn/problems/house-robber/
 * 打家劫舍
 */
public class L0198_HouseRobber {
    static class Solution {
        public int rob(int[] nums) {
            int n = nums.length;
            if (n == 1) {
                return nums[0];
            }

            // 动态规划 dp[i]为前i间房屋可偷盗最大金额，则 dp[i] = max(dp[i - 2] + nums[i], dp[i - 1])
//            int[] dp = new int[n];
//            dp[0] = nums[0];
//            dp[1] = Math.max(nums[0], nums[1]);
//            for (int i = 2; i < n; i ++) {
//                dp[i] = Math.max(dp[i - 2] + nums[i], dp[i - 1]);
//            }
//            return dp[n -1];

            // 简化存储
            int first = nums[0], second = Math.max(nums[0], nums[1]);
            for (int i = 2; i < n; i ++) {
                int temp = second;
                second = Math.max(first + nums[i], second);
                first = temp;
            }
            return second;

        }
    }
}
