package leetcode;

import leetcode.utils.TreeNode;

/**
 * @author : zhaochengming
 * @date : 2023/5/22 10:55
 * @description : https://leetcode.cn/problems/insufficient-nodes-in-root-to-leaf-paths/
 */
public class L1080_InsufficientNodes {
    static class Solution {
        public TreeNode sufficientSubset(TreeNode root, int limit) {
            boolean valid = dfs(root, 0, limit);
            return valid ? root : null;
        }

        private boolean dfs(TreeNode node, int sum, int limit) {
            if (node == null) return false;
            if (node.left == null && node.right == null) {
                return node.val + sum >= limit;
            }
            boolean leftValid = dfs(node.left, sum + node.val, limit);
            boolean rightValid = dfs(node.right, sum + node.val, limit);
            if (!leftValid) {
                node.left = null;
            }

            if (!rightValid) {
                node.right = null;
            }

            return leftValid || rightValid;
        }


    }
}
