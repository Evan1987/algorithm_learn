package leetcode;

/**
 * @author : zhaochengming
 * @date : 2023/5/19 17:55
 * @description : https://leetcode.cn/problems/maximum-sum-score-of-array/
 * 数组的最大得分
 */
public class L2219_MaxSumScoreOfArray {
    static class Solution {
        public long maximumSumScore(int[] nums) {
            long left = nums[0], right = 0;
            for (int num: nums) {
                right += num;
            }
            long ans = Math.max(left, right);

            for (int i = 0; i < nums.length - 1; i ++) {
                left += nums[i + 1];
                right -= nums[i];
                ans = Math.max(ans, Math.max(left, right));
            }

            return ans;
        }
    }
}
