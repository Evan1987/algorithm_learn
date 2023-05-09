package leetcode;

/**
 * @author : zhaochengming
 * @date : 2023/5/9 17:21
 * @description : https://leetcode.cn/problems/longest-substring-without-repeating-characters/
 * 无重复字符的最长子串
 */
public class L0003_LongestSubstrWithoutRepeatingCharacter {
    static class Solution {
        public int lengthOfLongestSubstring(String s) {
            int[] counts = new int[128];

            int start = 0, end = 0, ans = 0;
            while (end < s.length()) {
                int index = s.charAt(end);
                counts[index] ++;
                while (counts[index] > 1) {
                    counts[s.charAt(start ++)] --;
                }
                ans = Math.max(ans, end - start + 1);
                end ++;
            }

            return ans;

        }
    }
}
