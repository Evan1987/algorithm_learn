package leetcode;

import java.util.HashSet;
import java.util.Set;

/**
 * @author : zhaochengming
 * @date : 2023/6/15 16:52
 * @description : https://leetcode.cn/problems/largest-positive-integer-that-exists-with-its-negative/
 * 与对应负数同时存在的最大正整数
 */
public class L2441_LargestPosIntThatExistsWithItsNeg {
    static class Solution {
        public int findMaxK(int[] nums) {
            int ans = -1;
            Set<Integer> s = new HashSet<>();
            for (int num: nums) {
                if (s.contains(-num)) {
                    ans = Math.max(ans, Math.abs(num));
                } else {
                    s.add(num);
                }
            }
            return ans;
        }
    }
}
