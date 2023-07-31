package leetcode;

/**
 * @author : zhaochengming
 * @date : 2023/7/31 19:02
 * @description : https://leetcode.cn/problems/trapping-rain-water/
 * 接雨水
 */
public class L0042_TrappingRainWater {
    static class Solution {
        public int trap(int[] height) {
            int ans = 0;
            int n = height.length;
            if (n <= 2) return ans;

            // maxLeft[i] 位置i左侧最高高度（不含位置i） maxLeft[i] = max(maxLeft[i - 1], height[i - 1])
            // maxRight[i] 位置i右侧最高高度（不含位置i）maxRight[i] = max(maxRight[i + 1], height[i + 1])
            int[] maxLeft = new int[n];
            maxLeft[0] = 0;
            for (int i = 1; i < n - 1; i ++) {
                maxLeft[i] = Math.max(maxLeft[i - 1], height[i - 1]);
            }

            int[] maxRight = new int[n];
            maxRight[n - 1] = 0;
            for (int i = n - 2; i > 0; i --) {
                maxRight[i] = Math.max(maxRight[i + 1], height[i + 1]);
            }

            for (int i = 1; i < n - 1; i ++) {
                // 找两侧最低
                int min = Math.min(maxLeft[i], maxRight[i]);
                // 此处可以容纳的雨水量
                if (min > height[i]) ans += min - height[i];
            }

            return ans;
        }
    }
}
