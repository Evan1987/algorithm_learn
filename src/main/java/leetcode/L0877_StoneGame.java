package leetcode;


/**
 * @author : zhaochengming
 * @date : 2022/5/25 17:09
 * @description : https://leetcode.cn/problems/stone-game/
 */
public class L0877_StoneGame {

    static class Solution {
        public boolean stoneGame(int[] piles) {
            int n = piles.length;

            // dp[i][j]  代表  石子堆为piles[i ...j]时 当前玩家与另一玩家石子差可以达到的最大值
            int[][] dp = new int[n][n];
            for (int i = 0; i < n; i ++) {
                dp[i][i] = piles[i];
            }

            for (int i = n - 2; i >= 0; i --) {
                for (int j = i + 1;  j < n; j ++) {
                    dp[i][j] = Math.max(piles[i] - dp[i + 1][j], piles[j] - dp[i][j - 1]);
                }
            }

            return dp[0][n - 1] > 0;
        }
    }
}
