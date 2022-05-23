package leetcode;

import leetcode.utils.ListNode;

import java.util.*;

/**
 * @author : zhaochengming
 * @date : 2022/5/23 16:08
 * @description :
 */
public class MST0201_RemoveDuplicateNode {
    static class Solution {
        public ListNode removeDuplicateNodes(ListNode head) {
            if (head == null || head.next == null) return head;

            Set<Integer> seen = new HashSet<>();
            ListNode prev = head;
            ListNode curr = prev.next;
            seen.add(prev.val);
            while (curr != null) {
                if (!seen.add(curr.val)) {
                    curr = curr.next;
                    continue;
                }

                prev.next = curr;
                prev = curr;
                curr = curr.next;
            }

            prev.next = null;

            return head;
        }
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        int[] arr = {1, 2, 3, 3, 2, 1};
        ListNode head = ListNode.build(arr);
        ListNode curr = sol.removeDuplicateNodes(head);
        while (curr != null) {
            System.out.println(curr.val);
            curr = curr.next;
        }

    }

}
