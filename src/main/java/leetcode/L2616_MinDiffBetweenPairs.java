package leetcode;

import java.util.Arrays;

/**
 * @author : zhaochengming
 * @date : 2023/5/24 19:04
 * @description : https://leetcode.cn/problems/minimize-the-maximum-difference-of-pairs/
 * 最小化数对的最大差值
 */
public class L2616_MinDiffBetweenPairs {
    static class Solution {
        public int minimizeMax(int[] nums, int p) {
            if (p == 0) return 0;

            Arrays.sort(nums);
            int n = nums.length;

            // mid的范围
            int left = -1, right = nums[n - 1] - nums[0];
            while (left < right) {
                int mid = left + (right - left) / 2;
                int cnt = 0;
                for (int i = 0; i < n - 1; i ++) {
                    if (nums[i + 1] - nums[i] <= mid) {
                        cnt ++;
                        i ++;
                    }
                }

                if (cnt >= p) {
                    right = mid;
                } else {
                    left = mid + 1;
                }
            }

            return left;
        }
    }
}
