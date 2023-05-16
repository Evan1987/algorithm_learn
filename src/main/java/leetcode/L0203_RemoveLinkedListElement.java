package leetcode;

import leetcode.utils.ListNode;

/**
 * @author : zhaochengming
 * @date : 2023/5/16 11:31
 * @description : https://leetcode.cn/problems/remove-linked-list-elements/
 * 移除链表元素
 */
public class L0203_RemoveLinkedListElement {
    static class Solution {
        public ListNode removeElements(ListNode head, int val) {
            ListNode root = new ListNode(-1);
            root.next = head;
            ListNode prev = root;
            ListNode curr = head;

            while (curr != null) {
                if (curr.val == val) {
                    prev.next = curr.next;
                } else {
                    prev = curr;
                }
                curr = curr.next;
            }

            return root.next;

        }
    }
}
