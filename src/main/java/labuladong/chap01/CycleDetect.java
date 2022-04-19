package labuladong.chap01;

import labuladong.utils.Node;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : zhaochengming
 * @date : 2022/4/19 13:36
 * @description : 链表有环检测
 */
public class CycleDetect {

    // 快慢指针检测是否有环
    public static boolean hasCycle(Node<?> head) {
        Node<?> slow, fast;
        slow = fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (fast == slow) return true;
        }
        return false;
    }

    // 快慢指针检测环的起点
    public static <T> Node<T> detectCycle(Node<T> head) {
        Node<T> slow, fast;
        slow = fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (fast == slow) {
                break;
            }
        }

        if (fast == slow) {
            slow = head;
            while (slow != fast) {
                slow = slow.next;
                fast = fast.next;
            }

            return slow;
        }
        return null;
    }

    public static void main(String[] args) {
        String s = "1->2->3->4->5->6->7->8->4";
        Node<String> head = Node.hintBuild(s, "->");
        System.out.println(hasCycle(head));

        Node<String> start = detectCycle(head);
        if (start != null) {
            System.out.println(start.value);
        }
    }
}
