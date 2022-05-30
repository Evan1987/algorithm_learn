package leetcode;

import java.util.*;

/**
 * @author : zhaochengming
 * @date : 2022/5/30 14:53
 * @description : https://leetcode.cn/problems/new-21-game/
 */
public class L0837_New21Game {
    static class Solution {
        public double new21Game(int n, int k, int maxPts) {
            if (k == 0) return 1.0;
            int left = Math.max(n - maxPts + 1, 0), right = k - 1;
            if (left > right) return 1.0;

            return 0.0;
        }
    }
}
