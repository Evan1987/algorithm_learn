package leetcode;

import java.util.Arrays;

/**
 * @author : zhaochengming
 * @date : 2023/5/5 16:58
 * @description : https://leetcode.cn/problems/closest-subsequence-sum/
 * 最接近目标值的子序列和
 */
public class L1755_ClosestSubSeqSum {
    static class Solution {
        // 全局插入位置
        int point = 0;

        public int minAbsDifference(int[] nums, int goal) {
            int n = nums.length;
            // 拆分数组
            int mid = n / 2;
            int[] left = new int[1 << mid];
            int[] right = new int[1 << (n - mid)];
            track(nums, 0, mid - 1, 0, left);

            // 恢复插入位置
            this.point = 0;
            track(nums, mid, n - 1, 0, right);

            Arrays.sort(left);
            Arrays.sort(right);

            int ans = Integer.MAX_VALUE;
            // 枚举双侧组合
            int l = 0, r = right.length - 1;
            while (l < left.length && r >= 0) {
                int t = left[l] + right[r];
                int d;
                if (t > goal) {
                    r --;
                    d = t - goal;
                } else if (t < goal) {
                    l ++;
                    d = goal - t;
                } else {
                    return 0;
                }

                ans = Math.min(ans, d);
            }
            return ans;
        }

        /**
         * @param nums : 原始数组
         * @param start : 原始数组起始位置
         * @param end :原始数组结束位置
         * @param sum :子序列和
         * @param arr : 待插入序列
         * */
        private void track(int[] nums, int start, int end, int sum, int[] arr) {
            arr[point ++] = sum;
            for (int i = start; i <= end; i ++) {
                track(nums, i + 1, end, sum + nums[i], arr);
            }
        }
    }

    public static void main(String[] args) {
        int[] nums = {-6651,401,-8998,-9269,-9167,7741,-9699};
        int goal = 30536;
        Solution sol = new Solution();
        System.out.println(sol.minAbsDifference(nums, goal));
    }
}
