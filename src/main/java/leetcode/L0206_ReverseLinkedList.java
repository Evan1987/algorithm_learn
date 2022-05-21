package leetcode;

import leetcode.utils.ListNode;

/**
 * @author : zhaochengming
 * @date : 2022/5/21 15:13
 * @description : https://leetcode.cn/problems/reverse-linked-list/
 */
public class L0206_ReverseLinkedList {

    static class Solution {
        public ListNode reverseList(ListNode head) {
            if (head == null || head.next == null) return head;
            ListNode prev = null;
            while (head != null) {
                ListNode node = head.next;
                head.next = prev;
                prev = head;
                head = node;
            }

            return prev;
        }
    }
}

