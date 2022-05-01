package labuladong.chap03;


import labuladong.utils.TreeNode;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * @author : zhaochengming
 * @date : 2022/5/1 20:40
 * @description : 二叉树的中序遍历
 */
public class InOrderTraverse implements ITraverse {

    private void traverse(TreeNode<Integer> node, List<String> values) {
        if (node == null) {
            values.add(EMPTY);
            return;
        }

        traverse(node.left, values);
        values.add(String.valueOf(node.value));
        traverse(node.right, values);
    }

    @Override
    public String serialize(TreeNode<Integer> node) {
        List<String> values = new LinkedList<>();
        traverse(node, values);
        return StringUtils.join(values, SEP);
    }

    @Override
    public TreeNode<Integer> deserialize(String s) {
        // 中序遍历无法实现反序列化
        return null;
    }

    public static void main(String[] args) {
        InOrderTraverse obj = new InOrderTraverse();

        TreeNode<Integer> root = new TreeNode<>(1);
        root.left = new TreeNode<>(2);
        root.right = new TreeNode<>(3);
        root.left.right = new TreeNode<>(4);
        String s = obj.serialize(root);
        System.out.println(s);
    }
}
