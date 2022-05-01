package labuladong.chap03;

import labuladong.utils.TreeNode;

/**
 * @author : zhaochengming
 * @date : 2022/5/1 21:43
 * @description : Lowest Common Ancestor (最近公共祖先)
 */
public class LCA {

    public static TreeNode<Integer> solve(TreeNode<Integer> root, TreeNode<Integer> p, TreeNode<Integer> q) {
        if (root == null) {
            return null;
        }

        if (root.equals(p) || root.equals(q)) {
            return root;
        }

        TreeNode<Integer> left = solve(root.left, p, q);
        TreeNode<Integer> right = solve(root.right, p, q);
        if (left != null && right != null) {
            return root;
        }

        if (left == null && right == null) {
            return null;
        }

        return left == null ? right : left;
    }
}
