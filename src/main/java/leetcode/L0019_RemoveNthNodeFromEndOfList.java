package leetcode;

import leetcode.utils.ListNode;

/**
 * @author : zhaochengming
 * @date : 2023/5/16 17:40
 * @description : https://leetcode.cn/problems/remove-nth-node-from-end-of-list/
 * 删除链表的倒数第N个节点
 */
public class L0019_RemoveNthNodeFromEndOfList {
    static class Solution {
        public ListNode removeNthFromEnd(ListNode head, int n) {
            ListNode dummy = new ListNode(-1);
            dummy.next = head;
            ListNode fast = dummy, slow = dummy;
            int count = 1;
            while (count < n) {
                fast = fast.next;
                count ++;
            }

            ListNode prev = slow;
            while (fast.next != null) {
                if (fast.next.next == null) {
                    prev = slow;
                }
                fast = fast.next;
                slow = slow.next;
            }

            prev.next = slow.next;
            return dummy.next;
        }
    }
}
