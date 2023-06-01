package leetcode;

/**
 * @author : zhaochengming
 * @date : 2023/6/1 15:09
 * @description : https://leetcode.cn/problems/min-cost-climbing-stairs/
 * 使用最小的花费爬楼梯
 */
public class L0746_MInCostClimbingStairs {

    // dp算法，优化存储
    static class Solution {
        public int minCostClimbingStairs(int[] cost) {
            int n = cost.length;
            int prev1 = 0, prev2 = 0, target = 0;
            int i = 2;
            while (i <= n) {
                target = Math.min(prev1 + cost[i - 1], prev2 + cost[i - 2]);
                prev2 = prev1;
                prev1 = target;
                i ++;
            }
            return target;
        }
    }

    // dp算法
    static class Solution2 {
        public int minCostClimbingStairs(int[] cost) {
            int n = cost.length;
            int[] dp = new int[n + 1];   // dp[i] 达到台阶i所需的最小代价

            // 起始条件
            dp[0] = 0;
            dp[1] = 0;
            for (int i = 2; i <= n; i ++) {
                dp[i] = Math.min(dp[i - 2] + cost[i - 2], dp[i - 1] + cost[i - 1]);
            }
            return dp[n];
        }
    }


    // 暴力算法
    static class Solution3 {
        int ans = Integer.MAX_VALUE;

        public int minCostClimbingStairs(int[] cost) {
            track(cost, 0, 0);
            track(cost, 1, 0);
            return ans;
        }

        private void track(int[] cost, int start, int sum) {
            if (start >= cost.length) {
                ans = Math.min(ans, sum);
                return;
            }

            track(cost, start + 1, sum + cost[start]);
            track(cost, start + 2, sum + cost[start]);
        }
    }
}
