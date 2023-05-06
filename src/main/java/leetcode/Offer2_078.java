package leetcode;

import leetcode.utils.ListNode;

/**
 * @author : zhaochengming
 * @date : 2023/5/6 17:12
 * @description : https://leetcode.cn/problems/vvXgSW/
 * 合并排序链表
 */
public class Offer2_078 {
    static class Solution {

        // 分治合并
        public ListNode mergeKLists(ListNode[] lists) {
            return merge(lists, 0, lists.length - 1);
        }

        private ListNode merge(ListNode[] lists, int l, int r) {
            if (l == r) {
                return lists[l];
            }

            if (l > r) {
                return null;
            }

            int mid = l + (r - l) / 2;
            return mergeTwoList(merge(lists, l, mid), merge(lists, mid + 1, r));
        }

        /**
         * 合并两个有序链表
         * */
        private ListNode mergeTwoList(ListNode a, ListNode b) {
            if (a == null || b == null) {
                return a == null ? b : a;
            }

            ListNode head = new ListNode(0);
            ListNode tail = head, aPtr = a, bPtr = b;
            while (aPtr != null && bPtr != null) {
                if (aPtr.val < bPtr.val) {
                    tail.next = aPtr;
                    aPtr = aPtr.next;
                } else {
                    tail.next = bPtr;
                    bPtr = bPtr.next;
                }
                tail = tail.next;
            }

            tail.next = aPtr == null ? bPtr : aPtr;
            return head.next;
        }
    }

}
