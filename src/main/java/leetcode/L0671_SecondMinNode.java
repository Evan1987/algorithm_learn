package leetcode;

import leetcode.utils.TreeNode;


/**
 * @author : zhaochengming
 * @date : 2023/4/24 19:39
 * @description : https://leetcode.cn/problems/second-minimum-node-in-a-binary-tree/
 */
public class L0671_SecondMinNode {
    static class Solution {
        public int findSecondMinimumValue(TreeNode root) {

            int min = root.val;
            int ans = dfs(root, min);

            return ans == min ? -1 : ans;
        }

        private int dfs(TreeNode node, int min) {
            if (node == null) return min;
            if (node.val > min) return node.val;
            int leftMin = dfs(node.left, min);
            int rightMin = dfs(node.right, min);
            if (leftMin == min) {
                return rightMin;
            }
            if (rightMin == min) {
                return leftMin;
            }
            return Math.min(leftMin, rightMin);
        }
    }
}
