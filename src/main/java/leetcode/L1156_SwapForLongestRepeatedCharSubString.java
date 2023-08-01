package leetcode;

/**
 * @author : zhaochengming
 * @date : 2023/8/1 18:49
 * @description : https://leetcode.cn/problems/swap-for-longest-repeated-character-substring/
 * 单字符子串最大长度，最多只能交换一次任意两个字符
 */
public class L1156_SwapForLongestRepeatedCharSubString {
    static class Solution {
        public int maxRepOpt1(String text) {
            int n = text.length();
            int[] counts = new int[26];
            for (char c: text.toCharArray()) {
                counts[c - 'a'] ++;
            }

            int ans = 0;
            for (int i = 0; i < n;) {
                char c = text.charAt(i);

                // 寻找是否连续的子串
                int j = i + 1;
                while (j < n && text.charAt(j) == c) {
                    j ++;
                }

                int cnt = j - i;   // 当前连续子串长度

                // 看前后是否有空位，以及是否可以插入一个相同字符
                if (cnt < counts[c - 'a'] && (j < n || i > 0)) {
                    ans = Math.max(ans, cnt + 1);
                }

                // 看后面是否有只间隔1个的相同连续子串
                int k = j + 1;
                while (k < n && text.charAt(k) == c) {
                    k ++;
                }

                // 有可能两个连续串是所有的counts[c - 'a']
                // k - i表示有额外的字符c可以替换中间那个字符
                ans = Math.max(ans, Math.min(k - i, counts[c - 'a']));
                i = j;
            }

            return ans;
        }
    }
}
