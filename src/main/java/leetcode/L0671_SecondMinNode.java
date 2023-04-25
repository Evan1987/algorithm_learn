package leetcode;

import leetcode.utils.TreeNode;


/**
 * @author : zhaochengming
 * @date : 2023/4/24 19:39
 * @description : https://leetcode.cn/problems/second-minimum-node-in-a-binary-tree/
 */
public class L0671_SecondMinNode {
    static class Solution {
        int ans = -1;
        int rootValue;

        public int findSecondMinimumValue(TreeNode root) {

            rootValue = root.val;
            dfs(root);
            return ans;
        }

        private void dfs(TreeNode node) {
            if (node == null) return;
            if (ans != -1 && node.val > ans) return;
            if (node.val > rootValue) {
                ans = node.val;
                return;
            }
            dfs(node.left);
            dfs(node.right);
        }
    }
}
