package labuladong.chap03;

import labuladong.utils.Node;

/**
 * @author : zhaochengming
 * @date : 2022/5/3 22:01
 * @description :
 */
public class ReverseKGroup<T> {

    // 反转[a, b)之间的链表
    private Node<T> reverse(Node<T> head, Node<T> last) {
        Node<T> pre = null, cur = head, next;
        while (cur != last) {
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }

        return pre;
    }

    public Node<T> solve(Node<T> head, int k) {
        if (head == null) {
            return null;
        }

        Node<T> b = head;
        for (int i = 0; i < k; i ++) {
            // 不足k个
            if (b == null) {
                return head;
            }
            b = b.next;
        }

        // 反转前 k 个节点
        // newHead为新的头, head为新的尾
        Node<T> newHead = reverse(head, b);
        head.next = solve(b, k);
        return newHead;
    }

    public static void main(String[] args) {
        Node<String> head = Node.hintBuild("1,2,3,4,5,6,7", ",");
        ReverseKGroup<String> obj = new ReverseKGroup<>();
        Node<String> curr = obj.solve(head, 2);

        // 2,1,4,3,6,5,7
        while (curr != null) {
            System.out.println(curr.value);
            curr = curr.next;
        }
    }
}
