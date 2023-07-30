package leetcode;

import leetcode.utils.TreeNode;

/**
 * @author : zhaochengming
 * @date : 2023/7/30 11:39
 * @description : https://leetcode.cn/problems/convert-bst-to-greater-tree/
 * 将二叉搜索树转换为累加树
 */
public class L0538_ConvertBST2GreaterTree {
    static class Solution {
        public TreeNode convertBST(TreeNode root) {
            sumTrack(root, 0);
            return root;
        }

        private int sumTrack(TreeNode node, int sum) {
            if (node == null) return sum;

            // 根节点 + 右子树的总和
            node.val += sumTrack(node.right, sum);

            // 左节点 + 根节点更新后的值
            return sumTrack(node.left, node.val);
        }
    }
}
