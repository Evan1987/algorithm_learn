package labuladong.chap02;

import utils.annotations.WatchTime;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : zhaochengming
 * @date : 2022/4/26 20:22
 * @description : 添加正负号的方法数量
 */
public class SumWays {

    private static int res = 0;

    private static void backTrack(int[] nums, int start, int rest) {
        if (start == nums.length) {
            if (rest == 0) {
                res ++;
            }
            return;
        }

        // + nums[start]
        backTrack(nums, start + 1, rest - nums[start]);
        // - nums[start]
        backTrack(nums, start + 1, rest + nums[start]);
    }

    @WatchTime(methodDesc = "back track")
    public static int solve1(int[] nums, int target) {
        backTrack(nums, 0,  target);
        return res;
    }

    private static int dp(int[] nums, int start, int target, Map<String, Integer> memo) {
        // base case
        if (start == nums.length) {
            return target == 0 ? 1 : 0;
        }

        String key = start + "_" + target;
        if (memo.containsKey(key)) {
            return memo.get(key);
        }

        int res = dp(nums, start + 1, target - nums[start], memo)
                + dp(nums, start + 1, target + nums[start], memo);
        memo.put(key, res);
        return res;
    }

    @WatchTime(methodDesc = "dp")
    public static int solve2(int[] nums, int target) {
        Map<String, Integer> memo = new HashMap<>();
        return dp(nums, 0, target, memo);
    }

    // 子集之和正好为sum
    private static int subset(int[] nums, int sum) {
        int n = nums.length;

        // dp[i][j] 前i个数字凑出j的子集数
        int[][] dp = new int[n + 1][sum + 1];

        // base case: dp[0][.] = 0 dp[.][0] = 1
        for (int i = 0; i <= n; i ++) {
            dp[i][0] = 1;
        }

        for (int i = 1; i <= n; i ++) {
            for (int j = 1; j <= sum; j ++) {
                if (j < nums[i - 1]) {
                    dp[i][j] = dp[i - 1][j];
                } else{
                    dp[i][j] = dp[i - 1][j] + dp[i - 1][j - nums[i - 1]];
                }
            }
        }

        return dp[n][sum];
    }

    // 子集之和正好为sum, 1维dp
    private static int subset2(int[] nums, int sum) {
        int n = nums.length;
        int[] dp = new int[sum + 1];
        dp[0] = 1;

        for (int i = 0; i < n; i ++) {
            for (int j = sum; j >= 1; j --) {
                if (j >= nums[i]) {
                    dp[j] += dp[j - nums[i]];
                }
            }
        }

        return dp[sum];
    }


    // 子集背包问题
    @WatchTime(methodDesc = "sub-knapsack")
    public static int solve3(int[] nums, int target) {
        int sum = 0;
        for (int num: nums) {
            sum += num;
        }

        if (sum < target || (sum + target) % 2 != 0) {
            return 0;
        }

//        return subset(nums, (sum + target) / 2);
        return subset2(nums, (sum + target) / 2);
    }

    public static void main(String[] args) {
        int[] nums = {1, 3, 1, 4, 2};
        int target = 5;
        System.out.println(solve1(nums, target));
        System.out.println(solve2(nums, target));
        System.out.println(solve3(nums, target));
    }


}
