package leetcode;

import java.util.*;

/**
 * @author : zhaochengming
 * @date : 2022/5/28 13:42
 * @description : https://leetcode.cn/problems/simplified-fractions/
 */
public class L1447_SimplifiedFractions {
    static class Solution {

        public List<String> simplifiedFractions(int n) {
            List<String> ans = new LinkedList<>();
            while (n > 1) {
                ans.add(generate(1, n));
                for (int i = 2; i < n; i ++) {
                    if (gcd(i, n) == 1) ans.add(generate(i, n));
                }
                n --;
            }

            return ans;
        }

        private int gcd(int x, int y) {
            return y % x == 0 ? x : gcd(y % x, x);
        }

        private String generate(int numerator, int denominator) {
            return numerator + "/" + denominator;
        }
    }
}
