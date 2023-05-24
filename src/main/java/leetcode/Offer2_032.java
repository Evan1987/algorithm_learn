package leetcode;

/**
 * @author : zhaochengming
 * @date : 2023/5/24 15:38
 * @description : https://leetcode.cn/problems/dKk3P7/
 * 有效的变位词
 */
public class Offer2_032 {
    static class Solution {
        public boolean isAnagram(String s, String t) {
            if (s.equals(t) || s.length() != t.length()) return false;
            int[] counts = new int[26];
            for (char c: s.toCharArray()) {
                counts[c - 'a'] ++;
            }

            for (char c: t.toCharArray()) {
                counts[c - 'a'] --;
            }

            for (int num: counts) {
                if (num != 0) return false;
            }

            return true;

        }
    }
}
