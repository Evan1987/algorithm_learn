package leetcode;

import java.util.*;

/**
 * @author : zhaochengming
 * @date : 2022/5/23 16:34
 * @description : https://leetcode.cn/problems/maximum-sum-obtained-of-any-permutation/
 */
public class L1589_MaxSumObtainedOfAnyPermutation {

    static class Solution {

        private static final int MOD = (int) 1e9 + 7;

        public int maxSumRangeQuery(int[] nums, int[][] requests) {
            int n = nums.length;

            // 差分数组
            int[] counts = new int[n];
            for (int[] req: requests) {
                counts[req[0]] ++;
                if (req[1] + 1 < n) {
                    counts[req[1] + 1] --;
                }
            }

            // 还原为实际次数
            for (int i = 1; i < n; i ++) {
                counts[i] += counts[i - 1];
            }


            Arrays.sort(counts);
            Arrays.sort(nums);
            long ans = 0L;
            for (int i = n - 1; i >= 0 && counts[i] > 0; i --) {
                ans += (long) counts[i] * nums[i];
            }

            return (int)(ans % MOD);
        }


    }

    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 4, 5};
        int[][] requests = {{1, 3}, {0, 1}};
        Solution sol = new Solution();
        System.out.println(sol.maxSumRangeQuery(nums, requests));
    }
}
