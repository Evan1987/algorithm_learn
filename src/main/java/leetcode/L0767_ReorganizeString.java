package leetcode;


/**
 * @author : zhaochengming
 * @date : 2022/5/19 15:07
 * @description : https://leetcode.cn/problems/reorganize-string/
 */
public class L0767_ReorganizeString {
    static class Solution {
        public String reorganizeString(String s) {
            int n = s.length();
            if (n == 1) return s;

            int[] cnt = new int[26];
            for (char c: s.toCharArray()) {
                cnt[c - 'a'] ++;
            }

            // 有字母超过一定数量，就无法产生合理字符串
            for (int x: cnt) {
                if (x > (n + 1) / 2) return "";
            }

            int oddIndex = 1, evenIndex = 0;
            char[] ans = new char[n];
            for (int i = 0; i < 26; i ++) {
                if (cnt[i] == 0) continue;
                char c = (char) ('a' + i);

                if (cnt[i] <= n / 2) {
                    while (cnt[i] > 0 && oddIndex < n) {
                        ans[oddIndex] = c;
                        cnt[i] --;
                        oddIndex += 2;
                    }
                }

                while (cnt[i] > 0) {
                    ans[evenIndex] = c;
                    cnt[i] --;
                    evenIndex += 2;
                }
            }

            return new String(ans);
        }
    }
}
