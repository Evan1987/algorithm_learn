package labuladong.chap02;

import utils.annotations.WatchTime;

import java.util.Arrays;

/**
 * @author : zhaochengming
 * @date : 2022/4/20 13:09
 * @description : 最长递增子序列的长度
 */
public class LIS {

    // dp解法, O(n2)
    @WatchTime
    public static int solve1(int[] nums) {
        // dp[i] 代表以 nums[i]结尾的最长递增子序列长度
        int[] dp = new int[nums.length];
        Arrays.fill(dp, 1);

        // dp[i] 的值应该为 max(所有前面小于 nums[i] 的 LIS长度 + 1)
        for (int i = 0; i < dp.length; i ++) {
            for (int j = 0; j < i; j ++) {
                if (nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }

        int res = 0;
        for (int x: dp) {
            res = Math.max(res, x);
        }
        return res;
    }

    // 二分搜索解法 O(n* log n), 纸牌游戏
    @WatchTime
    public static int solve2(int[] nums) {

        // 排堆
        int[] top = new int[nums.length];
        Arrays.fill(top, 0);
        // 排堆数量
        int piles = 0;

        for (int poker: nums) {
            // 二分搜索边界
            int left = 0, right = piles;
            while (left < right) {
                int mid = left + (right - left) / 2;
                if (top[mid] < poker) {
                    left = mid + 1;
                } else if (top[mid] > poker) {
                    right = mid;
                } else {
                    right = mid;
                }
            }

            // 没有合适牌堆
            if (left == piles) {
                piles ++;
            }

            // 尽量放最左侧堆顶
            top[left] = poker;
        }

        return piles;
    }




    public static void main(String[] args) {
        int[] nums = {10, 9, 2, 5, 3, 7, 101, 18};
        System.out.println(solve1(nums));
        System.out.println(solve2(nums));

        int[] nums2 = {6, 3, 5, 10, 11, 2, 9, 14, 13, 7, 4, 8, 12};
        System.out.println(solve1(nums2));
        System.out.println(solve2(nums2));
    }

}
