package leetcode;

import java.util.Arrays;

/**
 * @author : zhaochengming
 * @date : 2023/5/8 19:44
 * @description : https://leetcode.cn/problems/check-if-an-array-is-consecutive/
 * 检查数组是否连贯
 */
public class L2229_CheckArrayConsecutive {
    static class Solution {
        public boolean isConsecutive(int[] nums) {
            Arrays.sort(nums);
            int prev = nums[0];
            for (int i = 1; i < nums.length; i ++) {
                if (nums[i] != ++prev) {
                    return false;
                }
            }
            return true;
        }
    }
}
