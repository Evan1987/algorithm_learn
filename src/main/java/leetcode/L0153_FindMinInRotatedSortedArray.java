package leetcode;

/*
 *
 * @author : zhaochengming.zcm
 * @date : 2025/12/9 23:35
 * https://leetcode.cn/problems/find-minimum-in-rotated-sorted-array/
 */
public class L0153_FindMinInRotatedSortedArray {
    static class Solution {
        public int findMin(int[] nums) {
            int n = nums.length;
            // 未翻转，包含长度为1的情况
            int left = 0;
            int right = n - 1;
            while (left < right) {
                int index = left + (right - left) / 2;
                if (nums[index] < nums[right]) {
                    right = index;
                } else {
                    left = index + 1;
                }
            }
            return nums[left];

            // 如果是 left <= right, 则只能left = right + 1时跳出，则可能会越过最小值，这时取nums[left - 1]即可
        }
    }
}
