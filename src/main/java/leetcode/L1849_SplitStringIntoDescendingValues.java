package leetcode;

/**
 * @author : zhaochengming
 * @date : 2023/4/24 14:57
 * @description : https://leetcode.cn/problems/splitting-a-string-into-descending-consecutive-values/
 */
public class L1849_SplitStringIntoDescendingValues {
    static class Solution {
        public boolean splitString(String s) {
            // strip left `0`
            int start = findNonZero(s, 0);
            if (start >= s.length() - 1) return false;    // 有效长度只剩1或者0都不满足

            s = s.substring(start);
            long cur = 0;
            for (int i = 0; i < s.length() / 2 + 1 && i < s.length() - 1; i ++) {
                cur = cur * 10 + s.charAt(i) - '0';
                if (dfs(s, i + 1, cur - 1)) {
                    return true;
                }
            }

            return false;
        }

        // 从start开始找到非0的索引
        private int findNonZero(String s, int start) {
            for (int i = start; i < s.length(); i ++) {
                if (s.charAt(i) != '0') {
                    return i;
                }
            }

            return s.length();
        }

        // 从start开始寻找target
        private boolean dfs(String s, int start, long target) {
            int n = s.length();
            if (start == n || (target == 0 && findNonZero(s, start) == n)) return true;
            long cur = 0;

            // 超过target就不用寻找了
            for (int i = start; i < n && cur <= target; i ++) {
                cur = cur * 10 + s.charAt(i) - '0';
                if (cur == target) {
                    return dfs(s, i + 1, target - 1);
                }
            }
            return false;
        }
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        String[] arr = {"15", "200100", "40003000200010", "1234", "050043", "9080701", "10009998"};
        for (String s: arr) {
            System.out.println(s + ": " + sol.splitString(s));
        }
    }
}
