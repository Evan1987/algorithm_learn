package leetcode;

import java.util.*;

/**
 * @author : zhaochengming
 * @date : 2022/5/16 12:51
 * @description : https://leetcode.cn/problems/count-number-of-homogenous-substrings/
 */
public class L1759_CountNumberOfHomogenousSubstrings {
    static class Solution {

        private static final long MOD = 1000000007L;

        public int countHomogenous(String s) {
            long ans = 0L;
            long count = 1L;
            char c = s.charAt(0);
            for (int i = 1; i < s.length(); i ++) {
                if (s.charAt(i) == c) {
                    count ++;
                } else {
                    ans += calNum(count);
                    ans %= MOD;
                    c = s.charAt(i);
                    count = 1;
                }
            }

            ans += calNum(count);
            ans %= MOD;
            return (int)ans;
        }

        private static long calNum(long n) {
            return n % 2 == 1 ? (n + 1) / 2 * n : n / 2 * (n + 1);
        }
    }
}
