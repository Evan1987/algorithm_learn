package leetcode;

import java.util.*;

/**
 * @author : zhaochengming
 * @date : 2022/5/28 14:14
 * @description : https://leetcode.cn/problems/minimum-number-of-days-to-eat-n-oranges/
 */
public class L1553_MinNumberOfDaysToEatNOranges {
    static class Solution {

        // Dijkstra
        public int minDays(int n) {
            PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> {
                if (a[0] == b[0]) {
                    return a[1] - b[1];
                }
                return a[0] - b[0];
            });

            Set<Integer> visited = new HashSet<>();
            pq.offer(new int[]{0, n});
            int ans;

            while (true) {
                int[] info = pq.poll();
                int days = info[0], rest = info[1];
                if (!visited.add(rest)) {
                    continue;
                }

                if (rest == 1) {
                    ans = days + 1;
                    break;
                }

                pq.offer(new int[]{days + rest % 2 + 1, rest / 2});
                pq.offer(new int[]{days + rest % 3 + 1, rest / 3});
            }

            return ans;
        }


    }
}
