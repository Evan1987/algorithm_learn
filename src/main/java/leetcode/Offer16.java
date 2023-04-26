package leetcode;

/**
 * @author : zhaochengming
 * @date : 2023/4/26 11:25
 * @description : https://leetcode.cn/problems/shu-zhi-de-zheng-shu-ci-fang-lcof/
 * 数值的整数次幂，实现 pow(x, n) ，即计算 x 的 n 次幂函数（即，x^n）。不得使用库函数，同时不需要考虑大数问题。
 */
public class Offer16 {
    static class Solution {
        public double myPow(double x, long n) {
            return n >=0 ? quickPow(x, n) : 1.0 / quickPow(x, -n);
        }

        private double quickPow(double x, long n) {
            double ans = 1.0;
            double contribute = x;
            while (n > 0) {
                if (n % 2 == 1) {
                    // 如果 N 二进制表示的最低位为 1，那么需要计入贡献
                    ans *= contribute;
                }

                contribute *= contribute;
                // 舍弃 N 二进制表示的最低位，这样我们每次只要判断最低位即可
                n /= 2;
            }
            return ans;
        }
    }
}
