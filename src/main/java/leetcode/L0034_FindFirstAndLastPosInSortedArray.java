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
            int leftBound = findLeftBound(nums, target);
            if (leftBound == -1) return new int[]{-1, -1};
            int rightBound = findRightBound(nums, target);
            return new int[]{leftBound, rightBound};

        }

        private int findLeftBound(int[] nums, int target) {
            int n = nums.length;
            if (n == 0) return -1;
            int lo = 0, hi = n - 1;
            while (lo <= hi) {
                int mid = lo + (hi - lo) / 2;
                if (nums[mid] < target) {
                    lo = mid + 1;
                } else if (nums[mid] > target) {
                    hi = mid - 1;
                } else {
                    hi = mid - 1;
                }
            }
            // left = right + 1停止循环left可能越出边界
            if (lo == n || nums[lo] != target) {
                return -1;
            }
            return lo;
        }

        private int findRightBound(int[] nums, int target) {
            int n = nums.length;
            if (n == 0) return -1;
            int lo = 0, hi = n - 1;
            while (lo <= hi) {
                int mid = lo + (hi - lo) / 2;
                if (nums[mid] < target) {
                    lo = mid + 1;
                } else if (nums[mid] > target){
                    hi = mid - 1;
                } else {
                    lo = mid + 1;
                }
            }
            // left = right + 1才停止循环，right可能越出左边界
            if (hi < 0 || nums[hi] != target) {
                return -1;
            }
            return hi;
        }
    }
}
