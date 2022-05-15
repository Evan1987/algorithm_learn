package leetcode;

import java.util.*;

/**
 * @author : zhaochengming
 * @date : 2022/5/15 15:51
 * @description : https://leetcode.cn/problems/keep-multiplying-found-values-by-two/
 */
public class L2154_KeepMultiplyingFoundValuesByTwo {

    static class Solution {
        public int findFinalValue(int[] nums, int original) {
            Arrays.sort(nums);
            int max = nums[nums.length - 1];
            if (original > max) {
                return original;
            }

            for (int x: nums) {
                if (x == original) {
                    original *= 2;
                    if (original > max) {
                        return original;
                    }
                }
            }
            return original;
        }
    }
}
