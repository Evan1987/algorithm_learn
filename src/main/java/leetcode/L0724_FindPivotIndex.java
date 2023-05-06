package leetcode;

/**
 * @author : zhaochengming
 * @date : 2023/5/6 16:45
 * @description : https://leetcode.cn/problems/find-pivot-index/
 * 寻找数组的中心下标
 */
public class L0724_FindPivotIndex {
    static class Solution {
        public int pivotIndex(int[] nums) {
            int sum = 0;
            for (int num: nums) {
                sum += num;
            }

            int left = 0;
            for (int i = 0; i < nums.length; i ++) {
                if (2 * left + nums[i] == sum) {
                    return i;
                }

                left += nums[i];
            }

            return -1;
        }
    }
}
