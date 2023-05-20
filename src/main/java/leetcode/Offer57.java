package leetcode;

/**
 * @author : zhaochengming
 * @date : 2023/5/20 12:29
 * @description : https://leetcode.cn/problems/he-wei-sde-liang-ge-shu-zi-lcof/
 * 和
 */
public class Offer57 {
    static class Solution {
        public int[] twoSum(int[] nums, int target) {
            int n = nums.length;
            int left = 0, right = n - 1;
            while (left < right) {
                int sum = nums[left] + nums[right];
                if (sum < target) {
                    left ++;
                } else if (sum > target) {
                    right --;
                } else {
                    return new int[]{nums[left], nums[right]};
                }
            }
            return new int[]{};
        }
    }
}
