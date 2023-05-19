package leetcode;

/**
 * @author : zhaochengming
 * @date : 2023/5/19 17:08
 * @description : https://leetcode.cn/problems/find-the-highest-altitude/
 * 找到最高海拔
 */
public class L1732_FindTheHighestAltitude {
    static class Solution {
        public int largestAltitude(int[] gain) {
            int ans = 0;

            int sum = 0;
            for (int x: gain) {
                sum += x;
                ans = Math.max(sum, ans);
            }

            return ans;

        }
    }
}
