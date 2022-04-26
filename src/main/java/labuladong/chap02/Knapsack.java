package labuladong.chap02;

/**
 * @author : zhaochengming
 * @date : 2022/4/25 13:55
 * @description : 0-1背包问题
 */
public class Knapsack {

    /**
     * @param W: 可装载的重量
     * @param wt: 物品重量
     * @param val: 物品价值
     * */
    public static int solve(int W, int[] wt, int[] val) {
        int n = wt.length;

        // dp[i][w] 前i个商品，重量限制为w情况下的最大价值
        int[][] dp = new int[n + 1][W + 1];

        // base case: dp[0][.], dp[.][0] 均为 0
        for (int i = 1; i <= n; i ++) {
            for (int w = 1; w <= W; w ++) {
                if (w < wt[i - 1]) {
                    dp[i][w] = dp[i - 1][w];
                } else {
                    dp[i][w] = Math.max(
                            dp[i - 1][w],                               // i商品没装进去
                            dp[i - 1][w - wt[i - 1]] + val[i - 1]       // i商品装进去了
                    );
                }
            }
        }

        return dp[n][W];
    }

    public static void main(String[] args) {
        int[] wt = {2, 1, 3};
        int[] val = {4, 2, 3};
        int W = 4;
        System.out.println(solve(W, wt, val));
    }
}
