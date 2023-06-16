package leetcode;

/**
 * @author : zhaochengming
 * @date : 2023/6/16 15:17
 * @description : https://leetcode.cn/problems/check-if-the-sentence-is-pangram/
 * 判断句子是否为全字母句
 */
public class L1832_CheckIfSentencePangram {
    static class Solution {
        public boolean checkIfPangram(String sentence) {
            int n = sentence.length();
            if (n < 26) return false;
            int[] letters = new int[26];
            int count = 0;
            for (int i = 0; i < n && i <= n - 26 + count; i ++) {
                int index = sentence.charAt(i) - 'a';
                if (letters[index] == 0) count ++;
                letters[index] ++;
            }

            return count == 26;
        }
    }
}
