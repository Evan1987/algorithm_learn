package leetcode;


/**
 * @author : zhaochengming
 * @date : 2022/5/17 14:01
 * @description : https://leetcode.cn/problems/check-if-array-is-sorted-and-rotated/
 */
public class L1752_CheckIfArraySortedAndRotated {
    static class Solution {
        public boolean check(int[] nums) {
            int n = nums.length;
            if (n <= 2) return true;

            int i = 1;
            while (i < n && nums[i] >= nums[i - 1]) i ++;
            for (; i < n; i ++) {
                if (nums[i] > nums[0]) return false;
                if (i < n - 1 && nums[i] > nums[i + 1]) return false;
            }
            return true;
        }
    }
}
