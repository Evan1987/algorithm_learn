package leetcode;

import java.util.Arrays;

/**
 * @author : zhaochengming
 * @date : 2022/5/8 20:57
 * @description : https://leetcode-cn.com/problems/permutation-in-string/
 */
public class L0567_PermutationInString {
    static class Solution {
        public boolean checkInclusion(String s1, String s2) {
            int n = s1.length();
            int m = s2.length();

            if (n > m) {
                return false;
            }

            int[] cnt = new int[26];
            for (int i = 0; i < n; i ++) {
                cnt[s1.charAt(i) - 'a'] ++;
                cnt[s2.charAt(i) - 'a'] --;
            }

            int diff = 0;
            for (int x: cnt) {
                if (x != 0) {
                    diff ++;
                }
            }

            if (diff == 0) {
                return true;
            }

            for (int i = n; i < m; i ++) {
                int x = s2.charAt(i - n) - 'a';
                int y = s2.charAt(i) - 'a';
                if (x == y) {
                    continue;
                }
                if (cnt[x] == 0) {
                    diff ++;
                } else if (cnt[x] == -1) {
                    diff --;
                }
                cnt[x] ++;

                if (cnt[y] == 0) {
                    diff ++;
                } else if (cnt[y] == 1) {
                    diff --;
                }

                cnt[y] --;

                if (diff == 0) {
                    return true;
                }

            }

            return false;
        }
    }

    public static void main(String[] args) {
        String s1 = "adc";
        String s2 = "dcda";
        Solution sol = new Solution();
        System.out.println(sol.checkInclusion(s1, s2));
    }
}
