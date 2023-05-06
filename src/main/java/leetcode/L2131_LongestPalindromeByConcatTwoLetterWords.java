package leetcode;

/**
 * @author : zhaochengming
 * @date : 2023/5/6 15:17
 * @description : https://leetcode.cn/problems/longest-palindrome-by-concatenating-two-letter-words/
 * 连接两字母单词得到的最长回文串
 */
public class L2131_LongestPalindromeByConcatTwoLetterWords {
    static class Solution {
        public int longestPalindrome(String[] words) {
            int ans = 0;
            int[][] counts = new int[26][26];
            for (String word: words) {
                int l = word.charAt(0) - 'a', r = word.charAt(1) - 'a';
                if (counts[r][l] > 0) {
                    counts[r][l] --;
                    ans += 4;
                } else {
                    counts[l][r] ++;
                }
            }

            // 是否有 aa bb这样的双重字符
            boolean flag = false;
            for (int i = 0; i < 26; i ++) {
                if (counts[i][i] > 0) {
                    flag = true;
                    break;
                }
            }

            return flag ? ans + 2 : ans;

        }

    }
}
