package leetcode;

/**
 * @author : zhaochengming
 * @date : 2023/6/16 11:27
 * @description : https://leetcode.cn/problems/check-if-the-number-is-fascinating/
 * 判断一个数是否迷人，str(n) + str(2n) + str(3n) 恰好包含数字1到9各1次，且不包含任何0
 */
public class L2729_CheckIfNumberFascinating {
    static class Solution {

        int[] nums = new int[10];
        int count = 0;

        public boolean isFascinating(int n) {
            if (n > 329 || n < 123) return false;
            return check(n) && check(2 * n) && check(3 * n) && count == 9;
        }

        private boolean check(int x) {
            while (x > 0) {
                int d = x % 10;
                if (d == 0 || nums[d] > 0) return false;
                nums[d] ++;
                count ++;
                x /= 10;
            }
            return true;
        }
    }
}
