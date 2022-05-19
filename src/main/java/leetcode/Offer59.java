package leetcode;

import java.util.*;

/**
 * @author : zhaochengming
 * @date : 2022/5/18 11:39
 * @description : https://leetcode.cn/problems/dui-lie-de-zui-da-zhi-lcof/
 */
public class Offer59 {
    static class MaxQueue {

        private Queue<Integer> queue;
        private Deque<Integer> deque;  // 维护单调

        public MaxQueue() {
            this.queue = new LinkedList<>();
            this.deque = new LinkedList<>();
        }

        public int max_value() {
            if (deque.isEmpty()) return -1;
            return deque.peekFirst();
        }

        public void push_back(int value) {
            queue.offer(value);
            while (!deque.isEmpty() && deque.peekLast() < value) {
                deque.pollLast();
            }

            deque.offerLast(value);
        }

        public int pop_front() {
            if (queue.isEmpty()) return -1;
            int ans = queue.poll();
            if (deque.peekFirst() == ans) {
                deque.pollFirst();
            }

            return ans;
        }
    }
}
