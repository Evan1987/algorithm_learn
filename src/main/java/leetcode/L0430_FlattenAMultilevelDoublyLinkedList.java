package leetcode;

import java.util.*;

/**
 * @author : zhaochengming
 * @date : 2022/5/8 18:17
 * @description : https://leetcode-cn.com/problems/flatten-a-multilevel-doubly-linked-list/
 */
public class L0430_FlattenAMultilevelDoublyLinkedList {
    static class Node {
        public int val;
        public Node prev;
        public Node next;
        public Node child;
    }

    static class Solution {

        private void addNext(Node prev, Node next) {
            prev.next = next;
            next.prev = prev;
        }

        public Node flatten(Node head) {

            if (head == null || (head.next == null && head.child == null)) {
                return head;
            }

            List<Node> track = new LinkedList<>();
            Deque<Node> stack = new LinkedList<>();
            stack.push(head);

            while (!stack.isEmpty()) {
                Node curr = stack.pop();
                track.add(curr);
                if (curr.next != null) {
                    stack.push(curr.next);
                }
                if (curr.child != null) {
                    stack.push(curr.child);
                }
            }

            head = track.remove(0);
            Node prev = head;
            for (Node node: track) {
                addNext(prev, node);
                prev.child = null;
                prev = node;
            }
            prev.child = null;
            prev.next = null;

            return head;
        }
    }
}
