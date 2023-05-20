package leetcode;

import leetcode.utils.ListNode;

/**
 * @author : zhaochengming
 * @date : 2023/5/20 18:06
 * @description : https://leetcode.cn/problems/sum-lists-lcci/
 * 链表数位加和
 */
public class MST0205_SumLists {
    static class Solution {
        public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
            ListNode head = new ListNode(0);
            ListNode curr = head;
            int add = 0;
            while (l1 != null && l2 != null) {
                int sum = l1.val + l2.val + add;
                add = sum / 10;
                curr.next = new ListNode(sum % 10);
                curr = curr.next;
                l1 = l1.next;
                l2 = l2.next;
            }

            ListNode node = l1 != null ? l1 : l2;

            while (node != null) {
                int sum = node.val + add;
                add = sum / 10;
                curr.next = new ListNode(sum % 10);
                curr = curr.next;
                node = node.next;
            }

            if (add > 0) {
                curr.next = new ListNode(add);
            }

            return head.next;
        }
    }
}
