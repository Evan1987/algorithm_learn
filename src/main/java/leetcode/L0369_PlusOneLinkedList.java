package leetcode;

import leetcode.utils.ListNode;

import java.util.Deque;
import java.util.LinkedList;

/**
 * @author : zhaochengming
 * @date : 2023/5/20 20:38
 * @description : https://leetcode.cn/problems/plus-one-linked-list/
 * 链表加1，给定一个用链表表示的非负整数， 然后将这个整数 再加上 1
 */
public class L0369_PlusOneLinkedList {
    static class Solution {
        public ListNode plusOne(ListNode head) {
            ListNode dummy = new ListNode(0);
            dummy.next = head;
            ListNode nonNine = dummy, node = dummy;
            while (node != null) {
                if (node.val != 9) nonNine = node;
                node = node.next;
            }

            nonNine.val ++;
            nonNine = nonNine.next;

            while (nonNine != null) {
                nonNine.val = 0;
                nonNine = nonNine.next;
            }

            return dummy.val == 0 ? dummy.next : dummy;

        }
    }
}
