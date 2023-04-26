package leetcode;

import java.util.Arrays;

/**
 * @author : zhaochengming
 * @date : 2023/4/26 10:52
 * @description : https://leetcode.cn/problems/3sum-smaller/submissions/
 * Given an array of n integers nums and a target, find the number of index triplets i, j, k with 0 <= i < j < k < n
 * that satisfy the condition nums[i] + nums[j] + nums[k] < target.
 * For example, given nums = [-2, 0, 1, 3], and target = 2.
 * Return 2. Because there are two triplets which sums are less than 2:
 * [-2, 0, 1], [-2, 0, 3]
 */
public class L0259_3SumSmaller {
    static class Solution {
        public int threeSumSmaller(int[] nums, int target) {
            int n = nums.length;
            int ans = 0;
            if (n < 3) return ans;
            Arrays.sort(nums);

            for (int i = 0; i < n - 2; i ++) {
                int x = nums[i];   // 最小值
                int l = i + 1;
                int r = n - 1;
                while (r > l) {
                    if (x + nums[l] + nums[r] >= target) {
                        r --;
                    } else {
                        ans += r - l;
                        l ++;
                    }
                }
            }
            return ans;

        }
    }

}
