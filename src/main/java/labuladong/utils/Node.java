package labuladong.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : zhaochengming
 * @date : 2022/4/19 13:37
 * @description :
 */
public class Node<T> {
    public T value;
    public Node<T> next;

    public Node(T v) {
        this.value = v;
        this.next = null;
    }

    public boolean hasNext() {
        return this.next != null;
    }

    public Node(Node<T> n){
        this.value = n.value;
        if (n.next != null) {
            this.next = new Node<>(n.next);
        }
    }

    public static Node<String> hintBuild(String s, String sep) {
        Node<String> head = new Node<>("");
        Map<String, Node<String>> nodes = new HashMap<>();
        Node<String> curr = head;
        for (String part: s.split(sep)) {
            Node<String> node = nodes.get(part);
            if (node == null) {
                node = new Node<>(part);
                nodes.put(part, node);
            }

            curr.next = node;
            curr = curr.next;
        }

        return head.next;
    }
}
