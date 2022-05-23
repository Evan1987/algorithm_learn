package leetcode.utils;

/**
 * @author : zhaochengming
 * @date : 2022/5/8 12:00
 * @description :
 */

public class ListNode {
    public int val;
    public ListNode next;
    public ListNode(int val, ListNode next) {
        this.val = val; this.next = next;
    }

    public ListNode(int val) {
        this(val, null);
    }

    public ListNode() {
        this(0);
    }

    public static ListNode build(int[] arr) {
        ListNode head = new ListNode();
        ListNode curr = head;
        for (int x: arr) {
            curr.next = new ListNode(x);
            curr = curr.next;
        }

        return head.next;
    }

    public void print() {
        ListNode curr = this;
        while (curr != null) {
            System.out.println(curr.val);
            curr = curr.next;
        }
    }

}

