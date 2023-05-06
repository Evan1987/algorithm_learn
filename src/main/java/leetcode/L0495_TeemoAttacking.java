package leetcode;

/**
 * @author : zhaochengming
 * @date : 2023/5/6 15:03
 * @description : https://leetcode.cn/problems/teemo-attacking/
 * 提莫攻击
 */
public class L0495_TeemoAttacking {
    static class Solution {
        public int findPoisonedDuration(int[] timeSeries, int duration) {
            int ans = 0;
            int start = timeSeries[0];
            int end = start + duration;
            for (int i = 1; i < timeSeries.length; i ++) {
                if (timeSeries[i] <= end) {
                    end = timeSeries[i] + duration;
                } else {
                    ans += (end - start);
                    start = timeSeries[i];
                    end = start + duration;
                }
            }
            return ans + end - start;

        }
    }
}
