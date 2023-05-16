package leetcode;

import java.util.Arrays;

/**
 * @author : zhaochengming
 * @date : 2023/5/16 17:30
 * @description : https://leetcode.cn/problems/minimum-difference-between-highest-and-lowest-of-k-scores/
 * 学生分数的最小差值
 */
public class L1984_MinDifferenceBetweenScores {
    static class Solution {
        public int minimumDifference(int[] nums, int k) {
            if (k == 1) return 0;
            int n = nums.length;
            Arrays.sort(nums);

            int ans = 100000;
            for (int i = k - 1; i < n; i ++) {
                ans = Math.min(ans, nums[i] - nums[i - k + 1]);
            }

            return ans;
        }
    }
}
