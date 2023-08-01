package leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : zhaochengming
 * @date : 2023/8/1 19:38
 * @description : https://leetcode.cn/problems/palindrome-permutation/
 * 是否可以通过排列组合变成回文串
 */
public class L0266_PalindromePermutation {
    static class Solution {
        public boolean canPermutePalindrome(String s) {
            Map<Character, Integer> counts = new HashMap<>();

            for (char c: s.toCharArray()) {
                counts.put(c, counts.getOrDefault(c, 0) + 1);
            }

            int odd = 0;
            for (int v: counts.values()) {
                if (v % 2 == 1) {
                    odd ++;
                    if (odd > 1) return false;
                }
            }

            return true;
        }
    }
}
