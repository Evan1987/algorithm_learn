package leetcode;


import leetcode.utils.ListNode;
/**
 * @author : zhaochengming
 * @date : 2025/12/13 00:41
 * @description : 两两交换链表中的元素
 * https://leetcode.cn/problems/swap-nodes-in-pairs/
 */
public class L0024_SwapNodesInPairs {
    static class Solution {
        public ListNode swapPairs(ListNode head) {
            if (head == null || head.next == null) return head;
            ListNode dummy = new ListNode(0, head);
            ListNode prev = dummy, curr = head;
            while (curr != null && curr.next != null) {
                ListNode next = curr.next;
                curr.next = next.next;
                next.next = curr;
                prev.next = next;

                prev = curr;
                curr = curr.next;
            }
            return dummy.next;
        }
    }

    public static void main(String[] args) {
        ListNode head = ListNode.build(new int[]{1, 2, 3, 4, 5});
        Solution sol = new Solution();
        ListNode newHead = sol.swapPairs(head);
        newHead.print();
    }
}
