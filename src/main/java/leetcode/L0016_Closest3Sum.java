package leetcode;

import java.util.*;

/**
 * @author : zhaochengming
 * @date : 2022/5/15 16:19
 * @description : https://leetcode.cn/problems/3sum-closest/
 */
public class L0016_Closest3Sum {
    static class Solution {
        public int threeSumClosest(int[] nums, int target) {
            int n = nums.length;

            Arrays.sort(nums);
            int ans = nums[0] + nums[1] + nums[2];

            if (ans >= target || n == 3) {
                return ans;
            }

            int rightEnd = n - 1;
            // 每次选择最小值
            for (int i = 0; i < n - 2 && ans != target; i ++) {
                int x = nums[i];

                if (i > 0 && x == nums[i - 1]) {
                    continue;
                }

                int residual = target - x;

                // 最小的2个和都比target大，可以跳出
                if (nums[i + 1] + nums[i + 2] > residual && Math.abs(nums[i + 1] + nums[i + 2] - residual) < Math.abs(target - ans)) {
                    ans = x + nums[i + 1] + nums[i + 2];
                    break;
                }

                // 最大的2个都比target小，可以略过
                if (nums[n - 1] + nums[n - 2] < residual && Math.abs(nums[n - 1] + nums[n - 2] - residual) < Math.abs(target - ans)) {
                    ans  = x + nums[n - 1] + nums[n - 2];
                    continue;
                }

                int left = i + 1;

                // 缩减宏观右边界
                while (rightEnd > left && x + nums[left] + nums[rightEnd - 1] >= target) rightEnd --;
                int right = rightEnd;

                while (left < right) {
                    int twoSum = nums[left] + nums[right];
                    if (Math.abs(twoSum - residual) < Math.abs(target - ans)) {
                        ans = x + twoSum;
                    }

                    if (twoSum > residual) {
                        int a = nums[right];
                        while (left < right && nums[right] == a) right --;
                    } else if (twoSum < residual) {
                        int b = nums[left];
                        while (left < right && nums[left] == b) left ++;
                    } else {
                        return target;
                    }
                }

            }

            return ans;

        }
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        int[] nums = {1, 2, 5, 10, 11};
        int target = 12;
        System.out.println(sol.threeSumClosest(nums, target));
    }
}
