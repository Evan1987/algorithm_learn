package leetcode;

import java.util.*;

/**
 * @author : zhaochengming
 * @date : 2022/5/23 12:42
 * @description : https://leetcode.cn/problems/smallest-k-lcci/
 */
public class MST1714_KSmallest {
    static class Solution {
        public int[] smallestK(int[] arr, int k) {
            if (k >= arr.length) return arr;
            if (k == 0) return new int[0];

            PriorityQueue<Integer> pq = new PriorityQueue<>(k, (o1, o2) -> o2 - o1);

            for (int i = 0; i < k; i ++) {
                pq.offer(arr[i]);
            }

            for (int i = k; i < arr.length; i ++) {
                if (pq.peek() > arr[i]) {
                    pq.poll();
                    pq.offer(arr[i]);
                }
            }

            return pq.stream().mapToInt(x -> x).toArray();

        }
    }
}
