package leetcode;

import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;

/**
 * @author : zhaochengming
 * @date : 2023/6/7 09:32
 * @description : https://leetcode.cn/problems/mice-and-cheese/
 * 老鼠和奶酪
 */
public class L2611_MiceAndCheese {
    static class Solution {

        static class Pair {
            public int diff;
            public int index;

            Pair(int diff, int index) {
                this.diff = diff;
                this.index = index;
            }
        }

        private int sum(int[] arr) {
            int res = 0;
            for (int x: arr) {
                res += x;
            }
            return res;
        }

        public int miceAndCheese(int[] reward1, int[] reward2, int k) {
            int n = reward1.length;
            if (k == 0) {
                return sum(reward2);
            }

            if (k == n) {
                return sum(reward1);
            }

            int ans = 0;
            PriorityQueue<Pair> pq = new PriorityQueue<>(Comparator.comparingInt(o -> o.diff));

            for (int i = 0; i < n; i ++) {
                int diff = reward1[i] - reward2[i];
                if (pq.size() < k) {
                    pq.offer(new Pair(diff, i));
                } else {
                    Pair head = pq.peek();
                    if (diff > head.diff) {
                        ans += reward2[head.index];
                        pq.poll();
                        pq.offer(new Pair(diff, i));
                    } else {
                        ans += reward2[i];
                    }
                }
            }

            for (Pair p: pq.toArray(new Pair[0])) {
                ans += reward1[p.index];
            }

            return ans;

        }
    }
}
