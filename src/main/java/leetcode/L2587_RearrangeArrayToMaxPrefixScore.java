package leetcode;

import java.util.Arrays;

/**
 * @author : zhaochengming
 * @date : 2023/4/26 09:29
 * @description : https://leetcode.cn/problems/rearrange-array-to-maximize-prefix-score/
 * 重排数组以得到最大前缀分数
 */
public class L2587_RearrangeArrayToMaxPrefixScore {
    static class Solution {
        public int maxScore(int[] nums) {
            Arrays.sort(nums);
            long prefix = 0;
            int n = nums.length;
            for (int i = n - 1; i >= 0; i --) {
                prefix += nums[i];
                if (prefix <= 0) {
                    return n - i - 1;
                }
            }

            return n;
        }
    }
}
