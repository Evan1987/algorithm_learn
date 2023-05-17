package leetcode;

/**
 * @author : zhaochengming
 * @date : 2023/5/17 09:49
 * @description : https://leetcode.cn/problems/max-consecutive-ones-ii/
 * 最多翻转一个0，连续1的最大个数
 */
public class L0487_MaxConsecutiveOnes {
    static class Solution {
        public int findMaxConsecutiveOnes(int[] nums) {
            int n = nums.length;
            // 简化空间
            int dp0 = 0, dp1 = 0, ans = 0;
            for (int num: nums) {
                if (num == 1) {
                    dp0 ++;
                    dp1 ++;
                } else {
                    dp1 = dp0 + 1;
                    dp0 = 0;
                }
                ans = Math.max(dp1, ans);
            }

            return ans;
        }
    }

    static class Solution2 {
        public int findMaxConsecutiveOnes(int[] nums) {
            int n = nums.length;

            // dp[i][0] 以i结尾的最大连续1个数且未翻转
            // dp[i][1] 以i结尾的最大连续1个数且已翻转
            // dp[i][0] = dp[i-1][0] + 1, nums[i] == 1; 0, nums[i] == 0
            // dp[i][1] = dp[i-1][1] + 1, nums[i] == 1; dp[i-1][0] + 1, nums[i] == 0
            int[][] dp = new int[n][2];
            dp[0][0] = nums[0] == 1 ? 1 : 0;
            dp[0][1] = 1;
            for (int i = 1; i < n; i ++) {
                if (nums[i] == 1) {
                    dp[i][0] = dp[i - 1][0] + 1;
                    dp[i][1] = dp[i - 1][1] + 1;
                } else {
                    dp[i][0] = 0;
                    dp[i][1] = dp[i - 1][0] + 1;
                }
            }

            int ans = 0;
            for (int i = 0; i < n; i ++) {
                ans = Math.max(ans, dp[i][1]);
            }

            return ans;
        }
    }
}
