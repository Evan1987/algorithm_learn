package leetcode;

import java.util.*;

/**
 * @author : zhaochengming
 * @date : 2022/5/25 18:50
 * @description : https://leetcode.cn/problems/longest-consecutive-sequence/
 */
public class L0128_LongestConsecutiveSequence {
    static class Solution {
        public int longestConsecutive(int[] nums) {
            if (nums.length == 0) return 0;

            Set<Integer> s = new HashSet<>();
            for (int x: nums) {
                s.add(x);
            }

            if (s.size() == 1) return 1;

            int count = 1;
            int max = 1;

            for (int x: s) {
                if (s.contains(x - 1)) continue;
                int curr = x;
                while (s.contains(++ curr)) {
                    count ++;
                }

                if (count > max) {
                    max = count;
                }
                count  = 1;
            }

            return max;
        }
    }
}
