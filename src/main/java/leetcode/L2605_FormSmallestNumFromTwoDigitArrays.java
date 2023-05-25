package leetcode;

import java.util.Arrays;

/**
 * @author : zhaochengming
 * @date : 2023/5/25 14:30
 * @description : https://leetcode.cn/problems/form-smallest-number-from-two-digit-arrays/
 * 从两个数字数组里生成最小的数字
 */
public class L2605_FormSmallestNumFromTwoDigitArrays {
    static class Solution {
        public int minNumber(int[] nums1, int[] nums2) {
            Arrays.sort(nums1);
            Arrays.sort(nums2);

            int ans;
            if (nums1[0] < nums2[0]) {
                ans = nums1[0] * 10 + nums2[0];
            } else if (nums1[0] > nums2[0]) {
                ans = nums2[0] * 10 + nums1[0];
            } else {
                return nums1[0];
            }

            int i = 0, j = 0;
            while (i < nums1.length && j < nums2.length) {
                if (nums1[i] < nums2[j]) {
                    i ++;
                } else if (nums1[i] > nums2[j]) {
                    j ++;
                } else {
                    return nums1[i];
                }
            }

            return ans;
        }
    }
}
