package leetcode;

import leetcode.utils.TreeNode;

/**
 * @author : zhaochengming
 * @date : 2023/8/2 11:20
 * @description : https://leetcode.cn/problems/sum-root-to-leaf-numbers/
 * 根节点到叶节点数字之和
 */
public class L0129_SumRootToLeafNumbers {
    static class Solution {
        int ans = 0;
        public int sumNumbers(TreeNode root) {
            dfs(root, 0);
            return ans;
        }

        private void dfs(TreeNode node, int score) {
            if (node == null) return;

            score = score * 10 + node.val;
            if (node.left == null && node.right == null) {
                ans += score;
            }

            dfs(node.left, score);
            dfs(node.right, score);
        }
    }
}
