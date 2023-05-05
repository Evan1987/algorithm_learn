package leetcode;

/**
 * @author : zhaochengming
 * @date : 2023/5/5 10:59
 * @description : https://leetcode.cn/problems/missing-number-in-arithmetic-progression/
 * 等差数列中缺失的数字
 */
public class L1228_MissingNumberInArithmeticProgression {
    static class Solution {
        public int missingNumber(int[] arr) {
            int n = arr.length;   // expect n + 1
            int d = (arr[n - 1] - arr[0]) / n;
            int sum = 0;
            for (int x: arr) {
                sum += x;
            }

            return (n + 1) * arr[0] + n * (n + 1) * d / 2 - sum;

        }
    }
}
