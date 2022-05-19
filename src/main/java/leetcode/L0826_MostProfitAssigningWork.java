package leetcode;

import org.jetbrains.annotations.NotNull;
import java.util.*;

/**
 * @author : zhaochengming
 * @date : 2022/5/18 14:10
 * @description : https://leetcode.cn/problems/most-profit-assigning-work/
 */
public class L0826_MostProfitAssigningWork {

    static class Solution {

        private static class Job implements Comparable<Job> {
            int difficult;
            int profit;

            Job(int difficult, int profit) {
                this.difficult = difficult;
                this.profit = profit;
            }

            @Override
            public int compareTo(@NotNull Job o) {
                return this.difficult - o.difficult;
            }
        }

        public int maxProfitAssignment(int[] difficulty, int[] profit, int[] worker) {

            int n = difficulty.length;
            Job[] jobs = new Job[n];
            for (int i = 0; i < n; i ++) {
                jobs[i] = new Job(difficulty[i], profit[i]);
            }

            Arrays.sort(jobs);
            Arrays.sort(worker);

            int ans = 0, best = 0, i = 0;
            for (int w: worker) {
                while (i < n && jobs[i].difficult <= w) {
                    best = Math.max(best, jobs[i].profit);
                    i ++;
                }
                ans += best;
            }

            return ans;

        }
    }

    public static void main(String[] args) {
        int[] difficulty = {2,4,6,8,10};
        int[] profit = {10,20,30,40,50};
        int[] worker = {4,5,6,7};
        Solution sol = new Solution();
        System.out.println(sol.maxProfitAssignment(difficulty, profit, worker));
    }
}
