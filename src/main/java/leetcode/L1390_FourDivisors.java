package leetcode;

/**
 * @author : zhaochengming
 * @date : 2023/6/16 14:10
 * @description : https://leetcode.cn/problems/four-divisors/
 * 四因数
 */
public class L1390_FourDivisors {
    static class Solution {
        public int sumFourDivisors(int[] nums) {
            int ans = 0;
            for (int num: nums) {
                ans += checkAndSum(num);
            }
            return ans;
        }

        private static int checkAndSum(int num) {
            int high = (int) Math.sqrt(num);
            // 判断完全平方数
            if (high * high == num) return 0;
            int sum = 1 + num;
            boolean flag = false;
            for (int i = 2; i <= high; i ++) {
                if (num % i == 0) {
                    if (flag) return 0;
                    sum += i + num / i;
                    flag = true;
                }
            }

            return flag ? sum: 0;
        }
    }
}
