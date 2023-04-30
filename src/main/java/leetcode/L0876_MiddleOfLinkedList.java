package leetcode;


import leetcode.utils.ListNode;
/**
 * @author : zhaochengming
 * @date : 2023/4/30 22:43
 * @description : https://leetcode.cn/problems/middle-of-the-linked-list/
 * 链表的中间节点
 */
public class L0876_MiddleOfLinkedList {
    static class Solution {
        public ListNode middleNode(ListNode head) {
            ListNode slow = head;
            ListNode fast = head;

            while (fast != null && fast.next != null) {
                fast = fast.next.next;
                slow = slow.next;
            }

            return slow;
        }
    }
}
