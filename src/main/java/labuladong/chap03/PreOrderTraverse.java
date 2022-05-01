package labuladong.chap03;


import labuladong.utils.TreeNode;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author : zhaochengming
 * @date : 2022/4/30 23:53
 * @description : 二叉树的前序遍历
 */
public class PreOrderTraverse implements ITraverse {

    private void traverse(TreeNode<Integer> node, List<String> values) {
        if (node == null) {
            values.add(EMPTY);
            return;
        }

        // 前序遍历
        values.add(String.valueOf(node.value));
        traverse(node.left, values);
        traverse(node.right, values);
    }

    public String serialize(TreeNode<Integer> root) {
        List<String> values = new LinkedList<>();
        traverse(root, values);
        return StringUtils.join(values, SEP);
    }

    private TreeNode<Integer> deserialize(LinkedList<String> values) {
        if (values.isEmpty()) {
            return null;
        }

        // 前序遍历
        String head = values.removeFirst();
        if (head.equals(EMPTY)) {
            return null;
        }

        TreeNode<Integer> node = new TreeNode<>(Integer.parseInt(head));
        node.left = deserialize(values);
        node.right = deserialize(values);
        return node;
    }

    public TreeNode<Integer> deserialize(String s) {
        LinkedList<String> values = new LinkedList<>(Arrays.asList(s.split(SEP)));
        return deserialize(values);
    }

    public static void main(String[] args) {

        PreOrderTraverse obj = new PreOrderTraverse();

        TreeNode<Integer> root = new TreeNode<>(1);
        root.left = new TreeNode<>(2);
        root.right = new TreeNode<>(3);
        root.left.right = new TreeNode<>(4);
        String s = obj.serialize(root);
        System.out.println(s);        // 1,2,#,4,#,#,3,#,#

        TreeNode<Integer> node = obj.deserialize(s);

        BST<Integer> tree1 = new BST<>(root);
        BST<Integer> tree2 = new BST<>(node);
        System.out.println(tree1.isSameTree(tree2));        // true
    }
}
