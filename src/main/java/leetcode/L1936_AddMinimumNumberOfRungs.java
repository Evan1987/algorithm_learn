package leetcode;

import java.util.*;

/**
 * @author : zhaochengming
 * @date : 2022/5/16 20:03
 * @description : https://leetcode.cn/problems/add-minimum-number-of-rungs/
 */
public class L1936_AddMinimumNumberOfRungs {
    static class Solution {
        public int addRungs(int[] rungs, int dist) {
            int ans = 0;
            int curr = 0;
            for (int rung: rungs) {
                if (rung - curr > dist) {
                    int t = (rung - curr) / dist;
                    ans += (rung - curr) % dist == 0 ? t - 1 : t;
                }
                curr = rung;
            }

            return ans;

        }
    }
}
