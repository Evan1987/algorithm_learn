package leetcode;

/**
 * @author : zhaochengming
 * @date : 2023/4/29 21:51
 * @description : https://leetcode.cn/problems/find-first-and-last-position-of-element-in-sorted-array/
 * 在排序数组中找到目标元素出现的第一个和最后一个位置
 */
public class L0034_FindFirstAndLastPosInSortedArray {
    static class Solution {
        public int[] searchRange(int[] nums, int target) {
            int minIndex = findMinIndex(nums, target);
            if (minIndex == -1) return new int[]{-1, -1};
            int maxIndex = findMaxIndex(nums, target);
            return new int[]{minIndex, maxIndex};

        }

        private int findMinIndex(int[] nums, int target) {
            int n = nums.length;
            if (n == 0) return -1;
            int lo = 0, hi = n - 1;
            while (lo < hi) {
                int mid = lo + (hi - lo) / 2;
                if (nums[mid] < target) {
                    lo = mid + 1;
                } else if (nums[mid] > target) {
                    hi = mid - 1;
                } else {
                    hi = mid;
                }
            }

            return nums[lo] == target ? lo : -1;
        }

        private int findMaxIndex(int[] nums, int target) {
            int n = nums.length;
            if (n == 0) return -1;
            int lo = 0, hi = n - 1;
            while (lo <= hi) {
                int mid = lo + (hi - lo) / 2;
                if (nums[mid] <= target) {
                    lo = mid + 1;
                } else {
                    hi = mid - 1;
                }
            }

            return nums[lo - 1] == target ? lo - 1 : -1;
        }
    }
}
