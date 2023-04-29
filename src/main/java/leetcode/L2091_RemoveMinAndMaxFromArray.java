package leetcode;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * @author : zhaochengming
 * @date : 2023/4/29 21:37
 * @description : https://leetcode.cn/problems/removing-minimum-and-maximum-from-array/
 */
public class L2091_RemoveMinAndMaxFromArray {
    static class Solution {
        public int minimumDeletions(int[] nums) {
            int n = nums.length;

            int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
            int minIndex = -1, maxIndex = -1;
            for (int i = 0; i < n; i ++) {
                if (nums[i] < min) {
                    min = nums[i];
                    minIndex = i;
                }

                if (nums[i] > max) {
                    max = nums[i];
                    maxIndex = i;
                }
            }

            int left = Math.min(minIndex, maxIndex);
            int right = Math.max(minIndex, maxIndex);

            // 从头部删除，从尾部删除，两侧删除
            int[] ans = {right + 1, n - left, left + 1 + n - right};
            Arrays.sort(ans);
            return ans[0];
        }
    }
}
