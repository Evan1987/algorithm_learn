package leetcode;

import leetcode.utils.TreeNode;

/**
 * @author : zhaochengming
 * @date : 2023/5/9 11:28
 * @description : https://leetcode.cn/problems/flip-equivalent-binary-trees/
 * 翻转等价二叉树
 */
public class L0951_FlipEquivalentBinaryTrees {
    static class Solution {

        public boolean flipEquiv(TreeNode root1, TreeNode root2) {
            if (root1 == null && root2 == null) {
                return true;
            }

            if (root1 == null || root2 == null || root1.val != root2.val) {
                return false;
            }

            return (flipEquiv(root1.left, root2.left) && flipEquiv(root1.right, root2.right)) ||
                    (flipEquiv(root1.right, root2.left) && flipEquiv(root1.left, root2.right));
        }
    }
}
