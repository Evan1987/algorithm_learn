package leetcode;

import leetcode.utils.ListNode;

import java.util.*;

/**
 * @author : zhaochengming
 * @date : 2022/5/8 12:02
 * @description : https://leetcode-cn.com/problems/next-greater-node-in-linked-list/
 */
public class L1019_NextGreaterNodeInLinkedList {

    static class Solution {

        private int[] toArray(ListNode head) {
            List<Integer> res = new ArrayList<>();
            ListNode node = head;
            while (node != null) {
                res.add(node.val);
                node = node.next;
            }

            return res.stream().mapToInt(Integer::intValue).toArray();
        }

        public int[] nextLargerNodes(ListNode head) {
            int[] headArr = toArray(head);
            int[] res = new int[headArr.length];
            Deque<Integer> stack = new LinkedList<>();

            int i = 0;
            ListNode node = head;
            while (node != null) {
                while (!stack.isEmpty() && headArr[stack.peek()] < node.val) {
                    res[stack.pop()] = node.val;
                }
                stack.push(i);
                node = node.next;
                i ++;
            }
            return res;
        }
    }

    public static void main(String[] args) {
        int[] headArr = {2,7,4,3,5};
        ListNode head = new ListNode(headArr[0]);
        ListNode node = head;
        for (int i = 1; i < headArr.length; i ++) {
            node.next = new ListNode(headArr[i]);
            node = node.next;
        }

        Solution sol = new Solution();
        // [7,0,5,5,0]
        for (int x: sol.nextLargerNodes(head)) {
            System.out.println(x);
        }
    }
}
