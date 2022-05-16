package leetcode;


/**
 * @author : zhaochengming
 * @date : 2022/5/15 0:36
 * @description : https://leetcode.cn/problems/ways-to-make-a-fair-array/
 */
public class L1664_WaysToMakeAFairArray {
    static class Solution {
        public int waysToMakeFair(int[] nums) {
            int n = nums.length;

            // diff[i]: nums[i] 及其前方的 奇数位 - 偶数位 之差
            int[] diff = new int[n];
            diff[0] = -nums[0];
            for (int i = 1; i < n; i ++) {
                diff[i] = diff[i - 1] + (i % 2 == 1 ? nums[i] : -nums[i]);
            }

            int ans = 0;
            for (int i = 0; i < n; i ++) {
                if (diff[i] - (i % 2 == 1 ? nums[i] : -nums[i]) - diff[n - 1] + diff[i] == 0) {
                    ans ++;
                }
            }

            return ans;
        }

        // 空间 O(1)做法
        public int waysToMakeFair2(int[] nums) {
            int n = nums.length;
            int diff = 0;  // 奇数偶数差

            for (int i = 0; i < n; i ++) {
                diff += i % 2 == 1 ? nums[i] : -nums[i];
            }

            int ans = 0;
            // 倒着遍历
            for (int i = n - 1; i >= 0; i --) {
                if (i % 2 == 1) {
                    diff -= nums[i];
                    if (diff == 0) ans ++;
                    diff -= nums[i];  // 由奇变偶
                } else {
                    diff += nums[i];
                    if (diff == 0) ans ++;
                    diff += nums[i];  // 由偶变奇
                }
            }

            return ans;
        }
    }
}
