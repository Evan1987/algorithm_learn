package leetcode.ubiquant;

import java.math.BigInteger;
import java.util.Arrays;

/**
 * @author : zhaochengming
 * @date : 2023/6/27 15:01
 * @description : https://leetcode.cn/contest/ubiquant2022/problems/uGuf0v/
 * 数字默契考验
 */
public class Q003_MinOP {
    static class Solution {
        public int minOperations(int[] numbers) {
            int n = numbers.length;
            if (n == 1) return 0;

            Arrays.sort(numbers);

            // 最大公约数
            int maxGCD = numbers[0];
            for (int i = 1; i < n; i ++) {
                maxGCD = gcd(maxGCD, numbers[i]);
            }
            for (int i = 0; i < n; i ++) {
                numbers[i] /= maxGCD;
            }

            // 最小公倍数
            long curr = numbers[0];
            for (int i = 1; i < n; i ++) {
                curr = curr * numbers[i] / gcd(curr, numbers[i]);
            }

            int ans = 0;
            for (int num: numbers) {
                int opNum = check(curr / num);
                if (opNum == -1) return -1;
                ans += opNum;
            }

            return ans;
        }

        // 检查倍数是否仅由2、3组成
        private static int check(long x) {
            int res = 0;
            while (x % 2 == 0) {
                x /= 2;
                res ++;
            }

            while (x % 3 == 0) {
                x /= 3;
                res ++;
            }

            return x == 1 ? res : -1;
        }

        private static int gcd(long x, long y) {
            return BigInteger.valueOf(x).gcd(BigInteger.valueOf(y)).intValue();
        }
    }
}
