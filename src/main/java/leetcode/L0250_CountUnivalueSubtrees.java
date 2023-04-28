package leetcode;

import leetcode.utils.TreeNode;


/**
 * @author : zhaochengming
 * @date : 2023/4/28 21:58
 * @description : https://leetcode.cn/problems/count-univalue-subtrees/
 * 统计同值子树，即叶子节点，或所有子节点与其根同值
 */
public class L0250_CountUnivalueSubtrees {
    static class Solution1 {
        int ans = 0;
        private boolean isUniVal(TreeNode node) {
            if (node.left == null && node.right == null) {
                ans ++;
                return true;
            }

            boolean uniVal = true;

            // 为了能够遍历所有节点，所以递归操作放在第一个进行判断，确保执行
            if (node.left != null) {
                uniVal = isUniVal(node.left) && uniVal && node.left.val == node.val;
            }

            if (node.right != null) {
                uniVal = isUniVal(node.right) && uniVal && node.right.val == node.val;
            }

            if (uniVal) {
                ans ++;
                return true;
            }
            return false;
        }

        public int countUnivalSubtrees(TreeNode root) {
            if (root == null) return 0;
            isUniVal(root);
            return this.ans;
        }
    }

    static class Solution2 {
        int ans = 0;
        private boolean isValidPart(TreeNode node, int val) {
            if (node == null) return true;

            // 用 | 确保都执行
            if (!isValidPart(node.left, node.val) | !isValidPart(node.right, node.val)) return false;
            ans ++;

            // at this point we know that this node is a univalue subtree of value node.val
            // pass a boolean indicating if this is a valid subtree for the parent node
            return node.val == val;
        }

        public int countUnivalSubtrees(TreeNode root) {
            isValidPart(root, 0);
            return this.ans;
        }
    }
}
