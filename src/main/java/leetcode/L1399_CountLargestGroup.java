package leetcode;

/**
 * @author : zhaochengming
 * @date : 2023/6/13 14:58
 * @description : https://leetcode.cn/problems/count-largest-group/
 * 统计最大组的数目
 */
public class L1399_CountLargestGroup {
    static class Solution {
        public int countLargestGroup(int n) {
            int[] nums = new int[9 * 4 + 1];
            for (int i = 1; i <= n; i ++) {
                nums[digitSum(i)] ++;
            }

            int max = 1;
            for (int num: nums) {
                max = Math.max(num, max);
            }

            int ans = 0;
            for (int num: nums) {
                if (num == max) ans ++;
            }

            return ans;
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
