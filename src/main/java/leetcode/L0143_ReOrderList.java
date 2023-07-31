package leetcode;

import leetcode.utils.ListNode;

/**
 * @author : zhaochengming
 * @date : 2023/7/31 10:39
 * @description : https://leetcode.cn/problems/reorder-list/
 */
public class L0143_ReOrderList {
    @SuppressWarnings("DuplicatedCode")
    static class Solution {
        public void reorderList(ListNode head) {
            if (head == null || head.next == null) return;

            ListNode mid = findMid(head);
            ListNode l1 = head;
            ListNode l2 = mid.next;
            mid.next = null;   // 切断中点

            // 中点之后的后端部分反转
            l2 = reverse(l2);

            // 开始将后端逐个塞入前端
            mergeList(l1, l2);
        }

        // 找链表中点
        private ListNode findMid(ListNode head) {
            // 快慢指针找到中点
            ListNode slow = head, fast = head;
            while (fast.next != null && fast.next.next != null) {
                slow = slow.next;
                fast = fast.next.next;
            }
            return slow;
        }

        // 反转链表
        private ListNode reverse(ListNode head) {
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

        // 合并链表
        public void mergeList(ListNode l1, ListNode l2) {
            ListNode l1Temp, l2Temp;
            while (l1 != null && l2 != null) {
                l1Temp = l1.next;
                l2Temp = l2.next;

                l1.next = l2;
                l1 = l1Temp;

                l2.next = l1;
                l2 = l2Temp;
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4};
        ListNode head = ListNode.build(arr);
        Solution sol = new Solution();
        sol.reorderList(head);
        head.print();
    }
}
