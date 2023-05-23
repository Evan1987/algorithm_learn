package leetcode;

/**
 * @author : zhaochengming
 * @date : 2023/5/23 19:25
 * @description : https://leetcode.cn/problems/squares-of-a-sorted-array/
 * 有序数组的平方
 */
public class L0977_SquareOfSortedArray {
    static class Solution {
        public int[] sortedSquares(int[] nums) {
            int n = nums.length;
            int[] ans = new int[n];

            int neg = -1;
            for (int i = 0; i < n; i ++) {
                if (nums[i] >= 0) break;
                neg = i;
            }

            int pos = neg + 1;
            int i = 0;
            while (neg >= 0 && pos < n) {
                if (-nums[neg] < nums[pos]) {
                    ans[i ++] = nums[neg] * nums[neg];
                    neg --;
                } else {
                    ans[i ++] = nums[pos] * nums[pos];
                    pos ++;
                }
            }

            while (neg >= 0) {
                ans[i ++] = nums[neg] * nums[neg];
                neg --;
            }

            while (pos < n) {
                ans[i ++] = nums[pos] * nums[pos];
                pos ++;
            }

            return ans;
        }
    }
}
