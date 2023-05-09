package leetcode;

/**
 * @author : zhaochengming
 * @date : 2023/5/9 11:22
 * @description : https://leetcode.cn/problems/merge-strings-alternately/
 * 交替合并字符串
 */
public class L1768_MergeStringsAlternately {
    static class Solution {
        public String mergeAlternately(String word1, String word2) {
            StringBuilder sb = new StringBuilder();
            int i = 0, j = 0;
            while (i < word1.length() && j < word2.length()) {
                sb.append(word1.charAt(i ++));
                sb.append(word2.charAt(j ++));
            }

            while (i < word1.length()) {
                sb.append(word1.charAt(i ++));
            }

            while (j < word2.length()) {
                sb.append(word2.charAt(j ++));
            }

            return sb.toString();

        }
    }
}
