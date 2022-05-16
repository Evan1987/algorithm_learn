package leetcode;

import java.util.*;

/**
 * @author : zhaochengming
 * @date : 2022/5/8 0:09
 * @description : https://leetcode-cn.com/problems/two-sum/
 */
public class L0001_TwoSum {
    static class Solution {
        public int[] twoSum(int[] nums, int target) {
            Map<Integer, Integer> m = new HashMap<>();
            m.put(nums[0], 0);
            for (int i = 1; i < nums.length; i ++) {
                int x = target - nums[i];
                if (m.containsKey(x)) {
                    return new int[]{m.get(x), i};
                }

                m.put(nums[i], i);
            }

            return new int[2];
        }
    }
}
