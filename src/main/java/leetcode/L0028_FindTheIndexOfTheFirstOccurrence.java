package leetcode;

/**
 * @author : zhaochengming
 * @date : 2025/12/13 10:32
 * @description : https://leetcode.cn/problems/find-the-index-of-the-first-occurrence-in-a-string/
 */
public class L0028_FindTheIndexOfTheFirstOccurrence {
    static class Solution {
        public int strStr(String haystack, String needle) {
            int n = haystack.length(), m = needle.length();
            if (n < m) return -1;
            if (m == 0) return 0;
            for (int i = 0; i + m <= n; i ++) {
                boolean flag = true;
                for (int j = 0; j < m; j ++) {
                    if (haystack.charAt(i + j) != needle.charAt(j)) {
                        flag = false;
                        break;
                    }
                }
                if (flag) return i;
            }
            return -1;
        }
    }
}
