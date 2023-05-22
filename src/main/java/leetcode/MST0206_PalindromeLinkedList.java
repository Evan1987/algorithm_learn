package leetcode;

import leetcode.utils.ListNode;

/**
 * @author : zhaochengming
 * @date : 2023/5/22 16:55
 * @description : https://leetcode.cn/problems/palindrome-linked-list-lcci/
 * 判断是否为回文链表
 */
public class MST0206_PalindromeLinkedList {
    // O(n)时间复杂度 O(1)空间复杂度
    static class Solution {
        public boolean isPalindrome(ListNode head) {
            if (head == null) return true;
            ListNode firstHalf = findHalfEnd(head);
            ListNode reversedHalf = reverseList(firstHalf.next);

            ListNode p1 = head, p2 = reversedHalf;
            boolean result = true;
            while (p1 != null && p2 != null) {
                if (p1.val != p2.val) {
                    result = false;
                    break;
                }
                p1 = p1.next;
                p2 = p2.next;
            }

            // 还原链表
            firstHalf.next = reverseList(reversedHalf);

            return result;

        }

        private ListNode findHalfEnd(ListNode head) {
            ListNode slow = head;
            ListNode fast = head;
            while (fast.next != null && fast.next.next != null) {
                slow = slow.next;
                fast = fast.next.next;
            }
            return slow;
        }

        private ListNode reverseList(ListNode head) {
            ListNode prev = null;
            ListNode curr = head;
            while (curr != null) {
                ListNode temp = curr.next;
                curr.next = prev;
                prev = curr;
                curr = temp;
            }
            return prev;
        }
    }
}
