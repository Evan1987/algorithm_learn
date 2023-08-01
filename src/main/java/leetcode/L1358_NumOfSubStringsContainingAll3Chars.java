package leetcode;

/**
 * @author : zhaochengming
 * @date : 2023/8/1 18:19
 * @description : https://leetcode.cn/problems/number-of-substrings-containing-all-three-characters/
 * 包含所有三种字母的子串数量
 */
public class L1358_NumOfSubStringsContainingAll3Chars {
    static class Solution {
        public int numberOfSubstrings(String s) {
            int ans = 0;
            int n = s.length();
            if (n < 3) return ans;
            int[] counts = new int[3];   // 记录3种字符的数量

            int left = 0;               // 当前子串的左边界
            for (int right = 0; right < n; right ++) {
                counts[s.charAt(right) - 'a'] ++;

                while (counts[0] >= 1 && counts[1] >= 1 && counts[2] >= 1) {
                    ans += n - right;                   // 当前满足的子串数量

                    // 右移左边界，当前左边界所在字符计数减少
                    counts[s.charAt(left ++) - 'a'] --;
                }
            }
            return ans;
        }
    }
}
