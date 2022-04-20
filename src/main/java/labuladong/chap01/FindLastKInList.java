package labuladong.chap01;

import labuladong.utils.Node;

/**
 * @author : zhaochengming
 * @date : 2022/4/19 22:46
 * @description : 寻找单向链表的倒数第K个元素
 */
public class FindLastKInList {

    public static <T> Node<T> solve(Node<T> head, int k) {
        Node<T> slow, fast;
        slow = fast = head;
        int i = 0;
        for (; i < k && fast != null; i ++) {
            fast = fast.next;
        }

        // 长度不足 k
        if (i < k) {
            return null;
        }

        while (fast != null) {
            slow = slow.next;
            fast = fast.next;
        }

        return slow;
    }

    public static void main(String[] args) {
        Node<String> node = Node.hintBuild("1->2->3->4->5->6", "->");
        Node<String> target1 = solve(node, 7);
        if (target1 != null) {
            System.out.println("target1: " + target1.value);
        }

        Node<String> target2 = solve(node, 3);
        if (target2 != null) {
            System.out.println("target2: " + target2.value);
        }

    }
}
