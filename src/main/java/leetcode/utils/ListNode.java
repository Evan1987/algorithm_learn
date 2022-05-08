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

}

