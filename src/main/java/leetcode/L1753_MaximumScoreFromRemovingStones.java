package leetcode;


import java.util.Arrays;

/**
 * @author : zhaochengming
 * @date : 2022/5/21 15:07
 * @description : https://leetcode.cn/problems/maximum-score-from-removing-stones/
 */
public class L1753_MaximumScoreFromRemovingStones {
    static class Solution {
        public int maximumScore(int a, int b, int c) {
            int sum = a + b + c;
            int[] nums = {a, b, c};
            Arrays.sort(nums);
            return Math.min(nums[0] + nums[1], sum / 2);
        }
    }
}
