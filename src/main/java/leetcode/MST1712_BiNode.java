package leetcode;

import leetcode.utils.TreeNode;

/**
 * @author : zhaochengming
 * @date : 2022/5/21 23:23
 * @description : https://leetcode.cn/problems/binode-lcci/
 */
public class MST1712_BiNode {
    static class Solution {

        private TreeNode prev = null;
        private TreeNode head = null;

        public TreeNode convertBiNode(TreeNode root) {
            if (root == null) return null;
            traverse(root);
            return head;
        }

        // 中序遍历, 保证按照升序 用visit函数访问各个节点
        private void traverse(TreeNode node) {
            if (node == null) return;

            traverse(node.left);
            vist(node);
            traverse(node.right);
        }

        private void vist(TreeNode node) {
            // 第一次访问，肯定是头结点
            if (prev == null) {
                head = node;
            } else {
                node.left = null;
                prev.right = node;
            }

            prev = node;
        }
    }
}
