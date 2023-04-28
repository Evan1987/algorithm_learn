package leetcode;

/**
 * @author : zhaochengming
 * @date : 2023/4/28 21:35
 * @description : https://leetcode.cn/problems/check-if-two-string-arrays-are-equivalent/
 * 检查两个字符串数组表示的字符串是否相同
 */
public class L1662_CheckIfTwoStringArraysEqual {
    static class Solution {
        public boolean arrayStringsAreEqual(String[] word1, String[] word2) {
            int i = 0, j = 0, ii = 0, jj = 0;
            while (i < word1.length && j < word2.length) {
                String s1 = word1[i], s2 = word2[j];
                if (s1.charAt(ii) != s2.charAt(jj)) {
                    return false;
                }

                ii ++;
                if (ii == s1.length()) {
                    ii = 0;
                    i ++;
                }

                jj ++;
                if (jj == s2.length()) {
                    jj = 0;
                    j ++;
                }

            }
            return i == word1.length && j == word2.length;
        }
    }
}
