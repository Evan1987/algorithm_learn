package leetcode;

import leetcode.utils.TreeNode;
import java.util.*;
/*
 * 二叉树中序遍历
 * @author : zhaochengming.zcm
 * @date : 2025/12/9 00:52
 * https://leetcode.cn/problems/binary-tree-inorder-traversal
 */
public class L0094_BinaryTreeInorderTraversal {
    static class Solution {

        private void inorder(TreeNode node, List<Integer> res) {
            if (node == null) return;
            inorder(node.left, res);
            res.add(node.val);
            inorder(node.right, res);
        }

        public List<Integer> inorderTraversal(TreeNode root) {
            List<Integer> res = new LinkedList<>();
            inorder(root, res);
            return res;
        }
    }
}
