package leetcode;

/**
 * @author : zhaochengming
 * @date : 2023/6/16 14:03
 * @description : https://leetcode.cn/problems/check-permutation-lcci/
 * 判定是否互为字符重排
 */
public class MST0102_CheckPermutation {
    static class Solution {
        public boolean CheckPermutation(String s1, String s2) {
            if (s1.length() != s2.length()) return false;
            int[] counts = new int[26];
            for (char c: s1.toCharArray()) {
                counts[c - 'a'] ++;
            }

            for (char c: s2.toCharArray()) {
                counts[c - 'a'] --;
                if (counts[c - 'a'] < 0) return false;
            }

            for (int num: counts) {
                if (num > 0) return false;
            }
            return true;

        }
    }
}
