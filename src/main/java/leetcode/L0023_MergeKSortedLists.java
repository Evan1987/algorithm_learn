package leetcode;

import leetcode.utils.ListNode;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * @author : zhaochengming
 * @date : 2022/5/20 10:07
 * @description : https://leetcode.cn/problems/merge-k-sorted-lists/
 */
public class L0023_MergeKSortedLists {
    static class Solution {

        private static class Status implements Comparable<Status> {
            int value;
            ListNode node;
            Status(int value, ListNode node) {
                this.value = value;
                this.node = node;
            }

            @Override
            public int compareTo(Status that) {
                return this.value - that.value;
            }
        }

        // 优先队列  时间O(kn × log k)， 空间 O(k)
        public ListNode mergeKLists3(ListNode[] lists) {
            int n = lists.length;
            if (n == 0) return null;
            if (n == 1) return lists[0];

            PriorityQueue<Status> pq = new PriorityQueue<>();
            for (ListNode node: lists) {
                if (node != null) pq.offer(new Status(node.val, node));
            }

            ListNode ans = new ListNode(), head = ans;
            while (!pq.isEmpty()) {
                Status status = pq.poll();
                head.next = new ListNode(status.value);
                ListNode node = status.node.next;
                if (node != null) {
                    pq.offer(new Status(node.val, node));
                }

                head = head.next;
            }

            return ans.next;
        }


        // 分治合并 时间 O(kn * log k)，空间 O(log k) 递归栈空间
        public ListNode mergeKLists2(ListNode[] lists) {
            int n = lists.length;
            if (n == 0) return null;
            return merge(lists, 0, n - 1);
        }

        private ListNode merge(ListNode[] lists, int left, int right) {
            if (left == right) return lists[left];
            if (left > right) return null;
            int mid = left + (right - left) / 2;
            return merge2Lists(merge(lists, left, mid), merge(lists, mid + 1, right));
        }

        // 顺序合并 时间O(k^2n) 空间 O(1)
        public ListNode mergeKLists(ListNode[] lists) {
            int n = lists.length;
            if (n == 0) return null;
            if (n == 1) return lists[0];

            ListNode ans = null;
            for (ListNode list : lists) {
                ans = merge2Lists(ans, list);
            }

            return ans;
        }

        private ListNode merge2Lists(ListNode list1, ListNode list2) {
            if (list1 == null || list2 == null) {
                return list1 != null ? list1 : list2;
            }

            ListNode head = new ListNode(), curr = head;
            while (list1 != null && list2 != null) {
                if (list1.val <= list2.val) {
                    curr.next = list1;
                    list1 = list1.next;
                } else {
                    curr.next = list2;
                    list2 = list2.next;
                }

                curr = curr.next;
            }

            curr.next = list1 != null ? list1 : list2;
            return head.next;
        }
    }
}
