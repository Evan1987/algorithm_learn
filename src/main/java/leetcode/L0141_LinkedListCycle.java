package leetcode;

import leetcode.utils.ListNode;

/**
 * @author : zhaochengming
 * @date : 2023/7/29 13:09
 * @description : https://leetcode.cn/problems/linked-list-cycle/
 */
public class L0141_LinkedListCycle {
    static class Solution {
        public boolean hasCycle(ListNode head) {
            if (head == null || head.next == null) return false;
            ListNode slow = head;
            ListNode fast = head;

            while (slow.next != null && fast.next != null && fast.next.next != null) {
                slow = slow.next;
                fast = fast.next.next;
                if (slow == fast) return true;
            }

            return false;
        }
    }
}
