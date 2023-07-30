package leetcode;


/**
 * @author : zhaochengming
 * @date : 2023/7/29 13:46
 * @description : https://leetcode.cn/problems/concatenation-of-consecutive-binary-numbers/
 * 连接连续二进制数字
 */
public class L1680_ConcatConsecutiveBinaryNumbers {
    static class Solution {
        public int concatenatedBinary(int n) {
            int MOD = 1_000_000_007;
            long ans = 0, shift = 0;
            for (int i = 1; i <= n; i ++) {
                if ((i & (i - 1)) == 0) {
                    shift ++;
                }
                ans = ((ans << shift) % MOD + i) % MOD;
            }

            return (int) ans;
        }
    }
}
