package leetcode;

import java.util.Arrays;

/**
 * @author : zhaochengming
 * @date : 2023/5/25 11:19
 * @description : https://leetcode.cn/problems/minimum-health-to-beat-game/
 * 通关游戏所需的最小生命值
 */
public class L2214_MinHPToBeatTheGame {
    static class Solution {
        public long minimumHealth(int[] damage, int armor) {
            long sum = 1;
            int minus = 0;

            for (int d: damage) {
                if (minus < armor) {
                    if (d < armor) {
                        minus = Math.max(d, minus);
                    } else {
                        minus = armor;
                    }
                }
                sum += d;
            }

            return sum - minus;
        }
    }
}
