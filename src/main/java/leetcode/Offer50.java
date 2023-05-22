package leetcode;

import java.util.Arrays;

/**
 * @author : zhaochengming
 * @date : 2023/5/22 16:08
 * @description : https://leetcode.cn/problems/di-yi-ge-zhi-chu-xian-yi-ci-de-zi-fu-lcof/
 * 第一个只出现一次的字符
 */
public class Offer50 {
    static class Solution {
        public char firstUniqChar(String s) {
            int n = s.length();
            if (n == 0) return ' ';
            int[] seen = new int[26];
            Arrays.fill(seen, -1);

            for (int i = 0; i < n; i ++) {
                int index = s.charAt(i) - 'a';
                if (seen[index] == -1) {
                    seen[index] = i;
                } else if (seen[index] < n) {
                    seen[index] = n;
                }
            }

            int minIndex = n;
            char c = ' ';
            for (int i = 0; i < 26; i ++) {
                if (seen[i] == n || seen[i] == -1) {
                    continue;
                }

                if (seen[i] < minIndex) {
                    minIndex = seen[i];
                    c = (char) ('a' + i);
                }
            }

            return c;

        }
    }
}
