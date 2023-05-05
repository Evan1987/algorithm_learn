package leetcode;

/**
 * @author : zhaochengming
 * @date : 2023/5/5 16:09
 * @description : https://leetcode.cn/problems/missing-element-in-sorted-array/
 * 有序数组中的缺失元素
 */
public class L1060_MissingElementInSortedArray {
    static class Solution {
        public int missingElement2(int[] nums, int k) {
            int n = nums.length;
            int i = 0;
            for (; i < n - 1; i ++) {
                int d = nums[i + 1] - nums[i] - 1;
                if (k > d) {
                    k -= d;
                } else {
                    break;
                }
            }

            return nums[i] + k;
        }

        private int getMissing(int[] nums, int index) {
            return nums[index] - nums[0] - index;
        }

        public int missingElement(int[] nums, int k) {
            int n = nums.length;
            int lo = 0, hi = n - 1;
            while (lo <= hi) {
                int mid = lo + (hi - lo) / 2;
                int gap = getMissing(nums, mid);
                if (gap < k) {
                    lo = mid + 1;
                } else if (gap > k) {
                    hi = mid - 1;
                } else {
                    hi = mid - 1;
                }
            }

            return nums[hi] + k - getMissing(nums, hi);

        }
    }
}
