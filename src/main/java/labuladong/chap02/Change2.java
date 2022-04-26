package labuladong.chap02;

/**
 * @author : zhaochengming
 * @date : 2022/4/26 14:39
 * @description : 零钱兑换Ⅱ，给定硬币面额和总金额amount，计算可以凑出amount的组合数，每种面额的硬币无限
 */
public class Change2 {

    public static int solve(int amount, int[] coins) {
        int n = coins.length;
        // dp[i][a], 使用前i种硬币凑出a的组合数
        int[][] dp = new int[n + 1][amount + 1];

        // base case: dp[0][.] = 0, dp[.][0] = 1
        for (int i = 0; i <= n; i ++) {
            dp[i][0] = 1;
        }

        for (int i = 1; i <= n; i ++) {
            for (int j = 1; j <= amount; j ++) {
                // 不使用 i - 1号硬币
                dp[i][j] = dp[i - 1][j];
                if (j >= coins[i - 1]) {
                    // 至少使用1枚 i - 1号硬币
                    dp[i][j] += dp[i][j - coins[i - 1]];
                }
            }
        }

        return dp[n][amount];
    }

    // 状态压缩
    public static int solve2(int amount, int[] coins) {
        int n = coins.length;
        // dp[i][a], 使用前i种硬币凑出a的组合数
        int[] dp = new int[amount + 1];

        // base case: dp[.][0] = 1
        dp[0] = 1;

        for (int i = 1; i <= n; i ++) {
            for (int j = 1; j <= amount; j ++) {
                if (j >= coins[i - 1]) {
                    // 至少使用1枚 i - 1号硬币
                    dp[j] += dp[j - coins[i - 1]];
                }
            }
        }

        return dp[amount];
    }



    public static void main(String[] args) {
        int[] coins = {5, 2, 1};
        int amount = 5;
        System.out.println(solve(amount, coins));
        System.out.println(solve2(amount, coins));
    }
}
