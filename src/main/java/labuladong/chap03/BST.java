package labuladong.chap03;

import labuladong.utils.TreeNode;

/**
 * @author : zhaochengming
 * @date : 2022/4/29 22:25
 * @description :
 */
public class BST<T extends Comparable<T>> {

    private TreeNode<T> root;

    public BST(TreeNode<T> root) {
        this.root = root;
    }

    public boolean isSameTree(BST<T> other) {
        return isSameTree(this.root, other.root);
    }

    public boolean isBST() {
        return isBST(this.root, null, null);
    }

    // BST中是否有该值
    public boolean contains(T target) {
        return contains(this.root, target);
    }

    // 在BST中插入一个值，如果有该值则忽略
    public void add(T val) {
        this.root = add(this.root, val);
    }

    public void delete(T val) {

    }


    private boolean isSameTree(TreeNode<T> root1, TreeNode<T> root2) {
        if (root1 == null && root2 == null) {
            return true;
        }

        if (root1 == null || root2 == null) {
            return false;
        }

        if (!root1.value.equals(root2.value)) {
            return false;
        }

        return isSameTree(root1.left, root2.left) && isSameTree(root1.right, root2.right);
    }

    private boolean isBST(TreeNode<T> root, TreeNode<T> min, TreeNode<T> max) {
        if (root == null) {
            return true;
        }

        if ((min != null && root.value.compareTo(min.value) <= 0)
                || (max != null) && root.value.compareTo(max.value) >= 0) {
            return false;
        }

        return isBST(root.left, min, root) && isBST(root.right, root, max);

    }

    private boolean contains(TreeNode<T> root, T target) {
        if (root == null) {
            return false;
        }

        int comp = root.value.compareTo(target);
        if (comp < 0) {
            return contains(root.right, target);
        } else if (comp > 0) {
            return contains(root.left, target);
        }

        return true;
    }

    private TreeNode<T> add(TreeNode<T> root, T val) {
        if (root == null) {
            return new TreeNode<>(val);
        }

        int comp = root.value.compareTo(val);
        if (comp < 0) {
            root.right = add(root.right, val);
        } else if (comp > 0) {
            root.left = add(root.left, val);
        }

        return root;
    }

    private TreeNode<T> delete(TreeNode<T> node, T val) {
        if (node == null) {
            return null;
        }

        int comp = node.value.compareTo(val);
        if (comp < 0) {
            node.right = delete(node.right, val);
        } else if (comp > 0) {
            node.left = delete(node.left, val);
        }

        // 命中
        if (node.left == null) return node.right;
        if (node.right == null) return node.left;
        TreeNode<T> t = node;
        node = min(t.right);
        node.left = t.left;
        node.right = delete(t.right, node.value);

        return node;

    }

    private TreeNode<T> min(TreeNode<T> node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

}
