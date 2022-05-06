package labuladong.chap05;

import utils.annotations.WatchTime;

import java.util.Arrays;

/**
 * @author : zhaochengming
 * @date : 2022/5/6 12:08
 * @description :
 */
public class CanJump {

    public static boolean solve(int[] nums) {
        int n = nums.length;
        int farthest = nums[0];

        for (int i = 1; i < n; i ++) {
            if (farthest < i) {
                return false;
            }

            farthest = Math.max(farthest, i + nums[i]);
        }

        return farthest >= n - 1;
    }

    // 最少要跳的步数
    @WatchTime(methodDesc = "dp")
    public static int minJump1(int[] nums) {
        int n = nums.length;
        // dp[i] 代表从 i 跳到 n - 1位置所需最少步数
        int[] dp = new int[n - 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        for (int i = n - 2; i >= 0; i --) {
            if (nums[i] >= n - 1 - i) {
                dp[i] = 1;
            } else {
                for (int j = 1; j <= nums[i]; j ++) {
                    if (dp[i + j] != Integer.MAX_VALUE) {
                        dp[i] = Math.min(dp[i], dp[i + j] + 1);
                    }
                }
            }
        }

        return dp[0] == Integer.MAX_VALUE ? -1 : dp[0];
    }

    @WatchTime(methodDesc = "greed")
    public static int minJump2(int[] nums) {
        int n = nums.length;
        int farthest = 0;
        int end = 0;
        int jump = 0;

        for (int i = 0; i < n - 1; i ++) {
            farthest = Math.max(nums[i] + i, farthest);

            if (end == i) {
                jump ++;
                end = farthest;
            }
        }

        return end >= n - 1 ? jump : -1;
    }

    public static void main(String[] args) {
        int[] nums1 = {2, 3, 1, 1, 4};
        int[] nums2 = {3, 2, 1, 0, 4};

        System.out.println(solve(nums1));
        System.out.println(minJump1(nums1));
        System.out.println(minJump2(nums1));
        System.out.println(solve(nums2));
        System.out.println(minJump1(nums2));
        System.out.println(minJump2(nums2));
    }
}
