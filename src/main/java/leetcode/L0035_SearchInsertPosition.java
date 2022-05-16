package leetcode;


/**
 * @author : zhaochengming
 * @date : 2022/5/14 23:48
 * @description : https://leetcode.cn/problems/search-insert-position/
 */
public class L0035_SearchInsertPosition {

    static class Solution {
        public int searchInsert(int[] nums, int target) {
            int left = 0;
            int right = nums.length;
            while (left < right) {
                int mid = left + (right - left) / 2;
                if (nums[mid] < target) {
                    left = mid + 1;
                } else if (nums[mid] > target) {
                    right = mid;
                } else {
                    return mid;
                }
            }

            return left;

        }
    }
}
