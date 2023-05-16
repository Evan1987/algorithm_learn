package leetcode;

import java.util.Arrays;

/**
 * @author : zhaochengming
 * @date : 2023/5/16 14:55
 * @description : https://leetcode.cn/problems/minimum-difficulty-of-a-job-schedule
 * 工作计划的最低难度
 */
public class L1335_MinDifficultyOfJobSchedule {

    // 动态规划
    static class Solution {
        public int minDifficulty(int[] jobDifficulty, int d) {
            int n = jobDifficulty.length;

            if (n < d) {
                return -1;
            }

            // dp[i][j] 前i + 1天完成 前j项工作的最小难度
            int[][] dp = new int[d + 1][n];
            for (int[] ints : dp) {
                Arrays.fill(ints, 10000);
            }

            // 初始化第一天
            int value = 0;
            for (int j = 0; j < n; j ++) {
                value = Math.max(value, jobDifficulty[j]);
                dp[0][j] = value;
            }

            // 推演后面的
            for (int i = 1; i < d; i ++) {
                for (int j = i; j < n; j ++) {
                    value = 0;
                    for (int k = j; k >= i; k --) {
                        value = Math.max(value, jobDifficulty[k]);
                        dp[i][j] = Math.min(dp[i][j], value + dp[i - 1][k - 1]);
                    }
                }
            }

            return dp[d - 1][n - 1];

        }
    }


    // 暴力求解，超出时间限制
    static class Solution1 {

        private int ANS = Integer.MAX_VALUE;

        public int minDifficulty(int[] jobDifficulty, int d) {
            if (jobDifficulty.length < d) {
                return -1;
            }

            track(jobDifficulty, 0, d, 0);
            return this.ANS;

        }

        private void track(int[] jobs, int start, int d, int sum) {
            if (d == 1) {
                int value = jobs[start];
                for (int i = start + 1; i < jobs.length; i ++) {
                    value = Math.max(value, jobs[i]);
                }

                sum += value;
                this.ANS = Math.min(this.ANS, sum);
                return;
            }

            int value = Integer.MIN_VALUE;
            for (int i = start; i < jobs.length - d + 1; i ++) {
                value = Math.max(value, jobs[i]);
                track(jobs, i + 1, d - 1, sum + value);
            }
        }
    }
}
