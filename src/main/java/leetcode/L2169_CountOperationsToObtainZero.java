package leetcode;


/**
 * @author : zhaochengming
 * @date : 2022/5/25 19:08
 * @description : https://leetcode.cn/problems/count-operations-to-obtain-zero/
 */
public class L2169_CountOperationsToObtainZero {
    static class Solution {
        public int countOperations(int num1, int num2) {
            if (num1 == 0 || num2 == 0) return 0;

            int ans = 0;
            while (num1 != num2 && num1 > 1 && num2 > 1) {
                if (num1 > num2) {
                    num1 -= num2;
                } else {
                    num2 -= num1;
                }
                ans ++;
            }

            if (num1 == num2) {
                return ans + 1;
            }

            return num1 == 1 ? ans + num2 : ans + num1;
        }

        // 辗转相除
        public int countOperations2(int num1, int num2) {

            if (num1 == 0 || num2 == 0) return 0;

            return num1 / num2 + countOperations2(num2, num1 % num2);

        }


    }
}
