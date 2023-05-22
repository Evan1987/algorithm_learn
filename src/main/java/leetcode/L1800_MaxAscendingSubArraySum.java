package leetcode;

/**
 * @author : zhaochengming
 * @date : 2023/5/22 16:45
 * @description : https://leetcode.cn/problems/maximum-ascending-subarray-sum/
 * 最大升序子数组和
 */
public class L1800_MaxAscendingSubArraySum {
    static class Solution {
        public int maxAscendingSum(int[] nums) {
            int ans = 0;
            int prev = 0, sum = 0;
            for (int x: nums) {
                if (x > prev) {
                    sum += x;
                } else {
                    ans = Math.max(ans, sum);
                    sum = x;
                }
                prev = x;
            }
            ans = Math.max(ans, sum);
            return ans;
        }
    }
}
