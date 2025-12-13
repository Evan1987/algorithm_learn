package leetcode;

/**
 * @author : zhaochengming
 * @date : 2025/12/13 19:20
 * @description : https://leetcode.cn/problems/minimum-equal-sum-of-two-arrays-after-replacing-zeros/
 */
public class L2918_MinSumEqualSumOfTwoArraysAfterReplacingZeros {
    static class Solution {
        public long minSum(int[] nums1, int[] nums2) {
            long sum1 = 0, sum2 = 0;
            int zero1 = 0, zero2 = 0;
            for (int x: nums1) {
                if (x == 0) zero1 ++;
                sum1 += x;
            }

            for (int x: nums2) {
                if (x == 0) zero2 ++;
                sum2 += x;
            }

            // 若 0 都替换为 1
            sum1 += zero1;
            sum2 += zero2;

            // 只有在没有0且无法到达另一数组底线时才输出-1
            if ((zero1 == 0 && sum1 < sum2) || (zero2 == 0 && sum2 < sum1)) {
                return -1L;
            }
            return Math.max(sum1, sum2);
        }
    }

    public static void main(String[] args) {
        int[] nums1 = new int[]{8,13,15,18,0,18,0,0,5,20,12,27,3,14,22,0};
        int[] nums2 = new int[]{29,1,6,0,10,24,27,17,14,13,2,19,2,11};
        System.out.println(new Solution().minSum(nums1, nums2));
    }
}
