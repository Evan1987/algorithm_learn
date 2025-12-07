package leetcode;

/**
 * @author : zhaochengming
 * @date : 2023/5/9 17:21
 * @description : <a href="https://leetcode.cn/problems/longest-substring-without-repeating-characters/">...</a>
 * 无重复字符的最长子串
 */
public class L0003_LongestSubstrWithoutRepeatingCharacter {
    static class Solution {
        public int lengthOfLongestSubstring(String s) {
            // 记录是否见过某字符
            boolean[] seen = new boolean[128];
            // 初始化指针和答案
            int start = 0, end = 0, ans = 0;
            // 主循环移动右指针
            while (end < s.length()) {
                // 即将要加到子串的字符c
                char c = s.charAt(end);
                // 如果之前见过字符c，说明出现了重复，则需要持续移动左指针，直到消除重复为止
                while (seen[c]) {
                    seen[s.charAt(start)] = false;
                    start ++;
                }
                // 正式加入子串
                seen[c] = true;
                // 更新答案
                ans = Math.max(ans, end - start + 1);
                end ++;
            }
            return ans;
        }
    }

    public static void main(String[] args) {
        String s = "abcadbd";
        System.out.println(new Solution().lengthOfLongestSubstring(s));
    }
}
