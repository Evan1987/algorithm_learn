package leetcode;


/**
 * @author : zhaochengming
 * @date : 2022/5/18 13:25
 * @description : https://leetcode.cn/problems/di-string-match/
 */
public class L0942_DIStringMatch {
    static class Solution {
        public int[] diStringMatch(String s) {
            int n = s.length();
            int[] ans = new int[n + 1];
            int lo = 0;
            int hi = n;
            int curr = 0;
            for (char c: s.toCharArray()) {
                if (c == 'D') {
                    ans[curr ++] = hi;
                    hi --;
                } else {
                    ans[curr ++] = lo;
                    lo ++;
                }
            }

            ans[curr] = hi;
            return ans;

        }
    }
}
