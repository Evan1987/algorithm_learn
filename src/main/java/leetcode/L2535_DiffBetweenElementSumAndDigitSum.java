package leetcode;

/**
 * @author : zhaochengming
 * @date : 2023/5/8 19:41
 * @description : https://leetcode.cn/problems/difference-between-element-sum-and-digit-sum-of-an-array/
 * 数组元素和与数位和的绝对差
 */
public class L2535_DiffBetweenElementSumAndDigitSum {
    static class Solution {
        public int differenceOfSum(int[] nums) {
            int ans = 0;
            for (int num: nums) {
                ans += num - digitSum(num);
            }
            return ans > 0 ? ans : -ans;
        }

        private static int digitSum(int x) {
            int sum = 0;
            while (x > 0) {
                sum += x % 10;
                x /= 10;
            }
            return sum;
        }
    }
}
