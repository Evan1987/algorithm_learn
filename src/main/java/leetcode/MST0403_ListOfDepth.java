package leetcode;

import leetcode.utils.ListNode;
import leetcode.utils.TreeNode;

import java.util.*;

/**
 * @author : zhaochengming
 * @date : 2022/5/15 15:25
 * @description : https://leetcode.cn/problems/list-of-depth-lcci/
 */
public class MST0403_ListOfDepth {
    static class Solution {
        public ListNode[] listOfDepth(TreeNode tree) {
            if (tree == null) {
                return new ListNode[]{};
            }

            List<ListNode> res = new LinkedList<>();

            Deque<TreeNode> q = new LinkedList<>();
            q.offerLast(tree);

            while (!q.isEmpty()) {
                int size = q.size();
                ListNode head = null;
                ListNode curr = null;
                for (int i = 0; i < size; i ++) {
                    TreeNode treeNode = q.pollFirst();
                    if (treeNode.left != null) {
                        q.offerLast(treeNode.left);
                    }

                    if (treeNode.right != null) {
                        q.offerLast(treeNode.right);
                    }

                    ListNode node = new ListNode(treeNode.val);

                    if (head == null) {
                        head = node;
                    } else {
                        curr.next = node;
                    }

                    curr = node;
                }
                res.add(head);
            }

            ListNode[] ans = new ListNode[res.size()];
            int i = 0;
            for (ListNode node: res) {
                ans[i ++] = node;
            }
            return ans;
        }
    }
}
