package leetcode;


import leetcode.utils.TreeNode;
/**
 * @author : zhaochengming
 * @date : 2023/5/6 11:01
 * @description : https://leetcode.cn/problems/closest-binary-search-tree-value/
 * 最接近的二叉搜索树值
 */
public class L0270_ClosestBinarySearchTreeValue {
    static class Solution {
        double diff = Integer.MAX_VALUE;
        int ans;

        public int closestValue(TreeNode root, double target) {
            dfs(root, target);
            return this.ans;
        }

        private void dfs(TreeNode node, double target) {
            if (node == null) {
                return;
            }

            double diff = Math.abs(target - node.val);
            if (diff < this.diff) {
                this.diff = diff;
                this.ans = node.val;
            } else if (diff == this.diff) {
                this.ans = Math.min(this.ans, node.val);
            }

            if (target > node.val) {
                dfs(node.right, target);
            } else if (target < node.val) {
                dfs(node.left, target);
            }
        }
    }
}
