package leetcode;

import java.util.Arrays;

/**
 * @author : zhaochengming
 * @date : 2023/5/6 09:34
 * @description : https://leetcode.cn/problems/delete-and-earn/
 * 删除并获得点数，可参考L0198 https://leetcode.cn/problems/house-robber 打家劫舍
 *
 */
public class L0740_DeleteAndEarn {
    static class Solution {
        public int deleteAndEarn(int[] nums) {
            int n = nums.length;
            Arrays.sort(nums);
            int[] points = new int[nums[n - 1] - nums[0] + 1];
            int ans = 0;
            // 找到不连续的片段
            int start = 0;
            int end = 0;
            int prev = nums[0];
            points[0] += nums[0];
            for (int i = 1; i < n; i ++) {
                int val = nums[i];
                int index = val - nums[0];
                points[val - nums[0]] += val;
                if (val > prev + 1) {
                    // 不连续片段生成
                    ans += rob(points, start, end);
                    start = index;
                }
                end = index;
                prev = val;
            }
            return ans + rob(points, start, end);
        }

        /**
         * 统计points[start: end + 1]内的打家劫舍最大值
         * @param points : 分数表
         * @param start : 统计起始指针
         * @param end : 统计结束指针
         * */
        private int rob(int[] points, int start, int end) {
            if (end == start) {
                return points[start];
            }

            int first = points[start], second = Math.max(points[start], points[start + 1]);
            for (int i = start + 2; i <= end; i ++) {
                int temp = second;
                second = Math.max(first + points[i], second);
                first = temp;
            }
            return second;
        }
    }
}
