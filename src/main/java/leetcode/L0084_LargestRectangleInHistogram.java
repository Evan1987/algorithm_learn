package leetcode;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

/**
 * @author : zhaochengming
 * @date : 2023/5/9 19:29
 * @description : https://leetcode.cn/problems/largest-rectangle-in-histogram/
 * 柱状图中最大的矩形，单调栈
 */
public class L0084_LargestRectangleInHistogram {
    static class Solution {
        public int largestRectangleArea(int[] heights) {
            int n = heights.length;
            int[] left = new int[n];   // 左边界
            int[] right = new int[n];  // 右边界

            Deque<Integer> monoStack = new LinkedList<>();   // 放入的是索引

            // 寻找左边界
            for (int i = 0; i < n; i ++) {
                while (!monoStack.isEmpty() && heights[monoStack.peek()] >= heights[i]) {
                    monoStack.pop();
                }
                left[i] = monoStack.isEmpty() ? -1 : monoStack.peek();  // -1 做左侧哨兵
                monoStack.push(i);
            }

            monoStack.clear();
            // 寻找右边界
            for (int i = n - 1; i >= 0; i --) {
                while (!monoStack.isEmpty() && heights[monoStack.peek()] >= heights[i]) {
                    monoStack.pop();
                }
                right[i] = monoStack.isEmpty() ? n : monoStack.peek();   // n 做右侧哨兵
                monoStack.push(i);
            }

            int ans = 0;
            for (int i = 0; i < n; i ++) {
                ans = Math.max(ans, (right[i] - left[i] - 1) * heights[i]);
            }
            return ans;
        }

        // 常数优化，减少一次遍历
        public int largestRectangleArea2(int[] heights) {
            int n = heights.length;
            int[] left = new int[n];
            int[] right = new int[n];
            Arrays.fill(right, n);

            Deque<Integer> monoStack = new LinkedList<>();
            for (int i = 0; i < n; i ++) {
                while (!monoStack.isEmpty() && heights[monoStack.peek()] >= heights[i]) {
                    right[monoStack.peek()] = i;
                    monoStack.pop();
                }

                left[i] = monoStack.isEmpty() ? -1 : monoStack.peek();
                monoStack.push(i);
            }

            int ans = 0;
            for (int i = 0; i < n; i ++) {
                ans = Math.max(ans, (right[i] - left[i] - 1) * heights[i]);
            }
            return ans;
        }
    }
}
