package leetcode;

import leetcode.utils.ListNode;


/**
 * @author : zhaochengming
 * @date : 2022/5/15 0:05
 * @description : https://leetcode.cn/problems/delete-middle-node-lcci/
 */
public class MST0203_DeleteMiddleNode {
    static class Solution {
        public void deleteNode(ListNode node) {
            node.val = node.next.val;
            node.next = node.next.next;
        }
    }
}
