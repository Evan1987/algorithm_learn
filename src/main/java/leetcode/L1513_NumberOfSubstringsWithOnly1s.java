package leetcode;

/**
 * @author : zhaochengming
 * @date : 2022/5/8 1:12
 * @description : https://leetcode-cn.com/problems/number-of-substrings-with-only-1s/
 */
public class L1513_NumberOfSubstringsWithOnly1s {

    static class Solution {

        private static final int MOD = (int) 1e9 + 7;

        private static long calc(long n) {
            return (n + 1) * n / 2;
        }

        public int numSub(String s) {
            long ans = 0;

            long count = 0;
            for (char x: s.toCharArray()) {
                if (x == '1') {
                    count ++;
                } else {
                    ans += calc(count);
                    ans %= MOD;
                    count = 0;
                }
            }

            if (count > 0) {
                ans += calc(count);
                ans %= MOD;
            }
            return (int)ans;
        }
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        System.out.println(sol.numSub("0110111"));
    }
}
