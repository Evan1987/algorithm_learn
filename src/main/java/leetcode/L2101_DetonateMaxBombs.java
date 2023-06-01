package leetcode;

import java.util.*;

/**
 * @author : zhaochengming
 * @date : 2023/6/1 11:38
 * @description : https://leetcode.cn/problems/detonate-the-maximum-bombs/
 * 最多引爆炸弹数
 */
public class L2101_DetonateMaxBombs {
    static class Solution {
        public int maximumDetonation(int[][] bombs) {
            Map<Integer, List<Integer>> connected = new HashMap<>();
            int n = bombs.length;
            for (int i = 0; i < n; i ++) {
                int[] a = bombs[i];
                long ra2 = (long) a[2] * a[2];
                for (int j = i + 1; j < n; j ++) {
                    int[] b = bombs[j];
                    long rb2 = (long) b[2] * b[2];
                    long d2 = (long) (b[0] - a[0]) * (b[0] - a[0]) + (long) (b[1] - a[1]) * (b[1] - a[1]);
                    if (d2 <= ra2) {
                        addConnected(connected, i, j);
                    }
                    if (d2 <= rb2) {
                        addConnected(connected, j, i);
                    }
                }
            }

            int ans = 0;
            for (int i = 0; i < n; i ++) {
                Deque<Integer> stack = new LinkedList<>();
                stack.offerLast(i);
                Set<Integer> seen = new HashSet<>();
                while (!stack.isEmpty()) {
                    Integer source = stack.pollLast();
                    if (seen.add(source)) {
                        List<Integer> targets = connected.get(source);
                        if (targets != null && !targets.isEmpty()) {
                            stack.addAll(targets);
                        }
                    }
                }

                ans = Math.max(ans, seen.size());
            }

            return ans;
        }

        private static void addConnected(Map<Integer, List<Integer>> connected, int source, int target) {
            if (!connected.containsKey(source)) {
                List<Integer> targets = new LinkedList<>();
                targets.add(target);
                connected.put(source, targets);
            } else {
                connected.get(source).add(target);
            }
        }
    }
}
