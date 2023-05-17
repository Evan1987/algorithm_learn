package leetcode;


import java.util.Arrays;

/**
 * @author : zhaochengming
 * @date : 2023/5/17 09:20
 * @description : https://leetcode.cn/problems/bu-ke-pai-zhong-de-shun-zi-lcof/
 * 判断是否为顺子
 */
public class Offer61 {
    static class Solution {
        public boolean isStraight(int[] nums) {
            Arrays.sort(nums);
            int start = -1;
            int prev = -1;
            for (int i = 0; i < nums.length; i ++) {
                if (nums[i] == 0) continue;
                if (start < 0) start = i;
                if (nums[i] != prev) {
                    prev = nums[i];
                } else {
                    return false;
                }
            }

            return nums[nums.length - 1] - nums[start] <= 4;
        }
    }
}
