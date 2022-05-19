package leetcode;


/**
 * @author : zhaochengming
 * @date : 2022/5/18 13:31
 * @description : https://leetcode.cn/problems/longest-palindromic-substring/
 */
public class L0005_LongestPalindromicSubstring {
    static class Solution {
        public String longestPalindrome(String s) {
            int n = s.length();
            if (n == 1) return s;

            // dp[i][j] 表示 s[i...j]是否是回文串
            boolean[][] dp = new boolean[n][n];
            for (int i = 0; i < n; i ++) {
                dp[i][i] = true;
            }

            int length = 1;
            int left;
            int right;
            left = right = 0;

            for (int i = n - 2; i >= 0; i --) {
                for (int j = i + 1; j < n; j ++) {
                    if (s.charAt(i) == s.charAt(j)) {
                        int len = j - i + 1;
                        dp[i][j] = i + 1 > j - 1 || dp[i + 1][j - 1];
                        if (dp[i][j] && len > length) {
                            length = len;
                            left = i;
                            right = j;
                        }
                    }
                }
            }

            return s.substring(left, right + 1);
        }

        private int find(String s, int left, int right) {
            while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
                left --;
                right ++;
            }

            return right - left - 1;
        }

        public String longestPalindrome2(String s) {
            int n = s.length();
            if (n == 1) return s;

            int left = 0, right = 0, length = 0;
            for (int i = 0; i < n; i ++) {
                int len1 = find(s, i, i);
                int len2 = find(s, i, i + 1);

                int len = Math.max(len1, len2);
                if (len > length) {
                    length = len;
                    left = i - (len - 1) / 2;
                    right = i + len / 2;
                }
            }

            return s.substring(left, right + 1);
        }
    }
}
