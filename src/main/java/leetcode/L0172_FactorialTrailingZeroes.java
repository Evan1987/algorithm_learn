package leetcode;


/**
 * @author : zhaochengming
 * @date : 2022/5/25 22:35
 * @description : https://leetcode.cn/problems/factorial-trailing-zeroes/
 */
public class L0172_FactorialTrailingZeroes {

    static class Solution {
        // 计算因子中 5的个数 （5 * 2才会出现10， 且 因子5的个数肯定小于2）
        public int trailingZeroes(int n) {
            int ans = 0;
            for (int i = 1; i <= n; i ++) {
                int x = i;
                while (x % 5 == 0) {
                    x /= 5;
                    ans ++;
                }
            }
            return ans;
        }

        // 加速算 5的因子数
        public int trailingZeroes2(int n) {
            int ans = 0;
            while (n >= 5) {
                n /= 5;
                ans += n;
            }
            return ans;
        }
    }
}
