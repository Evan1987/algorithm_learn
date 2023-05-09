package leetcode;

/**
 * @author : zhaochengming
 * @date : 2023/5/9 16:29
 * @description : https://leetcode.cn/problems/get-equal-substrings-within-budget/
 * 尽可能使字符串相等
 */
public class L1208_GetEqualSubstrWithinBudget {
    static class Solution {
        public int equalSubstring(String s, String t, int maxCost) {
            int n = s.length();
            int[] diff = new int[n];
            for (int i = 0; i < n; i ++) {
                diff[i] = Math.abs(s.charAt(i) - t.charAt(i));
            }

            int start = 0;
            int end = start;
            int sum = 0;
            int ans = 0;

            while (end < n) {
                sum += diff[end];
                while (sum > maxCost) {
                    sum -= diff[start ++];
                }
                ans = Math.max(ans, end - start + 1);
                end ++;
            }

            return ans;

        }
    }
}
