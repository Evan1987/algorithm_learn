package leetcode;

import java.util.*;

/**
 * @author : zhaochengming
 * @date : 2022/5/28 23:46
 * @description : https://leetcode.cn/problems/special-array-with-x-elements-greater-than-or-equal-x/
 */
public class L1608_SpecialArray {
    static class Solution {
        public int specialArray(int[] nums) {
            Arrays.sort(nums);
            int n = nums.length;
            int index = 0;
            for (int i = 1; i <= n; i ++) {
                if (n - index < i) break;
                for (; index < n; index ++) {
                    if (nums[index] >= i) {
                        if (n - index == i) return i;
                        break;
                    }
                }
            }

            return -1;
        }
    }
}
