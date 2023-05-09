package leetcode;

import java.util.PriorityQueue;

/**
 * @author : zhaochengming
 * @date : 2023/5/9 18:51
 * @description : https://leetcode.cn/problems/kth-largest-element-in-an-array/
 * 数组中第K大的数
 */
public class L0215_KthLargestElement {
    static class Solution {
        public int findKthLargest(int[] nums, int k) {
            PriorityQueue<Integer> pq = new PriorityQueue<>(k, Integer::compare);

            for (int i = 0; i < k; i ++) {
                pq.offer(nums[i]);
            }

            for (int i = k; i < nums.length; i ++) {
                if (nums[i] > pq.peek()) {
                    pq.poll();
                    pq.offer(nums[i]);
                }
            }
            return pq.peek();
        }
    }
}
