package leetcode;

import java.util.LinkedList;
import java.util.List;
import leetcode.utils.TreeNode;

/*
 * 二叉树后序遍历
 * @author : zhaochengming.zcm
 * @date : 2025/12/9 23:22
 * https://leetcode.cn/problems/binary-tree-postorder-traversal
 */
public class L0145_BinaryTreePostorderTraversal {
    static class Solution {

        private void dfs(TreeNode node, List<Integer> res) {
            if (node == null) return;
            dfs(node.left, res);
            dfs(node.right, res);
            res.add(node.val);
        }

        public List<Integer> postorderTraversal(TreeNode root) {
            List<Integer> res = new LinkedList<>();
            dfs(root, res);
            return res;
        }
    }

}
