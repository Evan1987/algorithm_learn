package leetcode;

/**
 * @author : zhaochengming
 * @date : 2023/6/14 14:14
 * @description : https://leetcode.cn/problems/partition-array-into-disjoint-intervals/
 * 分割数组
 */
public class L0915_PartitionArrayIntoDisjointIntervals {

    // 单次遍历
    static class Solution {
        public int partitionDisjoint(int[] nums) {
            int n = nums.length;
            int leftMax = nums[0], leftPos = 0, curMax = nums[0];
            for (int i = 1; i < n - 1; i ++) {
                curMax = Math.max(curMax, nums[i]);
                if (nums[i] < leftMax) {
                    leftPos = i;
                    leftMax = curMax;
                }
            }
            return leftPos + 1;
        }
    }

    // 两次遍历，但是先找到minIndex位置更利于优化
    static class Solution2 {
        public int partitionDisjoint(int[] nums) {
            int n = nums.length;
            // 找最小值所在位置
            int min = nums[0], minIndex = 0, max = nums[0], curMax = nums[0];
            for (int i = 1; i < n; i ++) {
                if (nums[i] > curMax) {
                    curMax = nums[i];
                } else if (nums[i] < min) {
                    min = nums[i];
                    minIndex = i;
                    max = curMax;
                }
            }

            int leftPos = minIndex;
            curMax = max;
            for (int i = minIndex + 1; i < n - 1; i ++) {
                if (nums[i] >= max) {
                    curMax = nums[i];
                } else {
                    leftPos = i;
                    max = curMax;
                }
            }

            return leftPos + 1;

        }
    }
}
