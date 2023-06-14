package leetcode;

/**
 * @author : zhaochengming
 * @date : 2023/6/14 17:23
 * @description : https://leetcode.cn/problems/calculate-amount-paid-in-taxes/
 * 计算应缴税额
 */
public class L2303_CalAmountPaidInTaxes {
    static class Solution {
        public double calculateTax(int[][] brackets, int income) {
            double ans = 0;
            int prev = 0;
            for (int[] bracket : brackets) {
                int upper = bracket[0];
                double rate = bracket[1] / 100.0;
                if (income > upper) {
                    ans += (upper - prev) * rate;
                } else {
                    ans += (income - prev) * rate;
                    break;
                }
                prev = upper;
            }
            return ans;
        }
    }
}
