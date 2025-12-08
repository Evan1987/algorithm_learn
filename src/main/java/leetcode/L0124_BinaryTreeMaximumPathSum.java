package leetcode;

import leetcode.utils.TreeNode;

/**
 * @author : zhaochengming
 * @date : 2022/5/8 20:10
 * @description : https://leetcode-cn.com/problems/binary-tree-maximum-path-sum/
 * 最大路径和
 */
public class L0124_BinaryTreeMaximumPathSum {

    static class Solution {

        private int MAX_NUM = Integer.MIN_VALUE;

        // 返回单边最大值: 1左侧 + 自身，2右侧 + 自身，3仅自身
        private int dfs(TreeNode node) {
            if (node == null) {
                return 0;
            }

            int left = dfs(node.left);
            int right = dfs(node.right);
            int res = max(left, right, 0) + node.val;

            // 以自身支路进行判断，不影响返回  res为单边, node.val+left+right为完整支路（无法与上层继续连接）
            MAX_NUM = max(MAX_NUM, res, node.val + left + right);
            return res;
        }

        private int max(int... nums) {
            int ans = nums[0];
            for (int i = 1; i < nums.length; i ++) {
                ans = Math.max(ans, nums[i]);
            }
            return ans;
        }


        public int maxPathSum(TreeNode root) {
            dfs(root);
            return MAX_NUM;
        }
    }
}
