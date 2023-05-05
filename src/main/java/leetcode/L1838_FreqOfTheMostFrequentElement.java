package leetcode;

import java.util.Arrays;

/**
 * @author : zhaochengming
 * @date : 2023/5/5 11:10
 * @description : https://leetcode.cn/problems/frequency-of-the-most-frequent-element/
 * k次操作后最高频元素的频数
 */
public class L1838_FreqOfTheMostFrequentElement {
    static class Solution {
        public int maxFrequency(int[] nums, int k) {
            Arrays.sort(nums);
            int n = nums.length;

            int ans = 1;  // 至少频次为
            long total = 0L;
            // 滑窗
            int l = 0;
            // 目标元素
            for (int i = 1; i < n; i ++) {
                // nums[i]为目标元素，窗口为 [l, i]
                // 前序窗口要增加的量
                total += (long) (nums[i] - nums[i - 1]) * (i - l);
                // 超过操作数，需要移动l
                while (total > k) {
                    total -= (nums[i] - nums[l]);
                    l ++;
                }
                ans = Math.max(ans, i - l + 1);
            }
            return ans;

        }
    }
}
