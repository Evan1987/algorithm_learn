package leetcode;

/**
 * @author : zhaochengming
 * @date : 2023/5/19 18:27
 * @description : https://leetcode.cn/problems/minimum-operations-to-make-the-array-increasing/
 * 最少操作使数组严格递增
 */
public class L1827_MinOperationsToMakeArrayIncreasing {
    static class Solution {
        public int minOperations(int[] nums) {
            int ans = 0;
            int prev = nums[0];
            for (int i = 1; i < nums.length; i ++) {
                if (nums[i] <= prev) {
                    ans += prev + 1 - nums[i];
                    prev = prev + 1;
                } else {
                    prev = nums[i];
                }
            }
            return ans;
        }
    }
}
