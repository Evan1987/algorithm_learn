package leetcode;

import leetcode.utils.TreeNode;

import java.util.Deque;
import java.util.LinkedList;

/**
 * @author : zhaochengming
 * @date : 2023/5/19 10:45
 * @description : https://leetcode.cn/problems/subtree-of-another-tree/
 * 是否是另一棵树的子树
 */
public class L0572_SubTreeOfAnotherTree {
    static class Solution {
        public boolean isSubtree(TreeNode root, TreeNode subRoot) {
            Deque<TreeNode> q = new LinkedList<>();
            q.offer(root);

            while (!q.isEmpty()) {
                TreeNode node = q.poll();
                if (isEqual(node, subRoot)) return true;
                if (node.left != null) q.offer(node.left);
                if (node.right != null) q.offer(node.right);
            }
            return false;
        }

        private boolean isEqual(TreeNode a, TreeNode b) {
            if (a == null && b == null) return true;
            if (a == null || b == null) return false;
            return a.val == b.val && isEqual(a.left, b.left) && isEqual(a.right, b.right);
        }
    }
}
