package leetcode;

/**
 * @author : zhaochengming
 * @date : 2023/7/31 19:47
 * @description : https://leetcode.cn/problems/longest-continuous-increasing-subsequence/
 * 最长连续递增子序列
 */
public class L0674_LongestContinuousIncreasingSubSeq {
    static class Solution {
        public int findLengthOfLCIS(int[] nums) {
            int ans = 0;
            int prev = Integer.MIN_VALUE;
            int count = 0;
            for (int num: nums) {
                if (num > prev) {
                    count ++;
                } else {
                    ans = Math.max(ans, count);
                    count = 1;
                }
                prev = num;
            }

            ans = Math.max(ans, count);
            return ans;
        }
    }
}
