package labuladong.chap03;

import labuladong.utils.Node;

/**
 * @author : zhaochengming
 * @date : 2022/5/3 20:29
 * @description : 判断回文链表，无法倒着遍历
 */
public class Palindrome {

    private static Node<Integer> left;

    private static boolean traverse(Node<Integer> node) {
        if (node == null) {
            return true;
        }

        // 会一直遍历至最右
        boolean res = traverse(node.next);
        res = res && (left.value.equals(node.value));
        left = left.next;
        return res;
    }

    public static boolean solve1(Node<Integer> head) {
        left = head;
        return traverse(head);
    }

    private static <T> Node<T> reverse(Node<T> head) {
        Node<T> pre = null, cur = head;
        while (cur != null) {
            Node<T> next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        return pre;
    }

    // 快慢指针找中点
    public static boolean solve2(Node<Integer> head) {
        Node<Integer> slow, fast;
        slow = fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // 判断奇偶情况
        // 奇数，此时slow正好在中点
        if (fast != null) {
            slow = slow.next;
        }

        // 翻转slow之后的链表
        Node<Integer> left = head;
        Node<Integer> right = reverse(slow);

        while (right != null) {
            if (!left.value.equals(right.value)) {
                return false;
            }

            left = left.next;
            right = right.next;
        }

        return true;
    }

    private static Node<Integer> buildNode(int[] values) {
        if (values.length == 0) {
            return null;
        }
        Node<Integer> head = new Node<>(values[0]);
        Node<Integer> curr = head;
        for (int i = 1; i < values.length; i ++) {
            curr.next = new Node<>(values[i]);
            curr = curr.next;
        }
        return head;
    }

    public static void main(String[] args) {
        Node<Integer> head1 = buildNode(new int[]{1, 2, 3, 2, 1});
        Node<Integer> head2 = buildNode(new int[]{1, 2, 2, 1});
        Node<Integer> head3 = buildNode(new int[]{1, 2, 3, 4, 2, 1});

        System.out.println(solve1(head1) + " " + solve1(head2) + " " + solve1(head3));
        System.out.println(solve2(head1) + " " + solve2(head2) + " " + solve2(head3));


    }
}
