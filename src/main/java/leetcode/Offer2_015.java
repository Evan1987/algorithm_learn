package leetcode;

import java.util.LinkedList;
import java.util.List;

/**
 * @author : zhaochengming
 * @date : 2023/5/24 15:46
 * @description : https://leetcode.cn/problems/VabMRr/
 * 字符串中所有的变位词
 */
public class Offer2_015 {
    static class Solution {
        public List<Integer> findAnagrams(String s, String p) {
            List<Integer> ans = new LinkedList<>();
            int m = s.length(), n = p.length();
            if (m < n) return ans;

            int[] counts = new int[26];
            for (char c: p.toCharArray()) {
                counts[c - 'a'] ++;
            }

            for (int i = 0; i < n; i ++) {
                counts[s.charAt(i) - 'a'] --;
            }

            int flags = 0;
            for (int num: counts) {
                if (num != 0) flags ++;
            }

            if (flags == 0) {
                ans.add(0);
            }

            int left = 0, right = n - 1;
            while (right < m - 1) {
                int outIndex = s.charAt(left) - 'a';
                counts[outIndex] ++;
                if (counts[outIndex] == 1) {
                    flags ++;
                } else if (counts[outIndex] == 0) {
                    flags --;
                }
                left ++;

                right ++;
                int inIndex = s.charAt(right) - 'a';
                counts[inIndex] --;
                if (counts[inIndex] == -1) {
                    flags ++;
                } else if (counts[inIndex] == 0) {
                    flags --;
                }

                if (flags == 0) {
                    ans.add(left);
                }
            }

            return ans;
        }
    }

    public static void main(String[] args) {
        String a = "baa", b = "aa";
        Solution sol = new Solution();
        System.out.println(sol.findAnagrams(a, b));
    }
}
