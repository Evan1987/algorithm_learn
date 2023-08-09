package leetcode;

/**
 * @author : zhaochengming
 * @date : 2023/8/9 22:56
 * @description : https://leetcode.cn/problems/subtract-the-product-and-sum-of-digits-of-an-integer/
 * 整数的各位积和之差
 */
public class L1281_SubtractProductAndSumOfDigitsOfAnInteger {
    static class Solution {
        public int subtractProductAndSum(int n) {
            int prod = 1;
            int sum = 0;

            while (n > 0) {
                int x = n % 10;
                prod *= x;
                sum += x;
                n /= 10;
            }

            return prod - sum;
        }
    }
}
