package leetcode;

import java.util.LinkedList;
import java.util.List;
import leetcode.utils.TreeNode;
/*
 * 二叉树前序遍历
 * @author : zhaochengming.zcm
 * @date : 2025/12/9 23:19
 * https://leetcode.cn/problems/binary-tree-preorder-traversal/
 */
public class L0144_BinaryTreePreorderTraversal {
    static class Solution {

        private void dfs(TreeNode node, List<Integer> res) {
            if (node == null) return;
            res.add(node.val);
            dfs(node.left, res);
            dfs(node.right, res);
        }

        public List<Integer> preorderTraversal(TreeNode root) {
            List<Integer> res = new LinkedList<>();
            dfs(root, res);
            return res;
        }
    }
}
