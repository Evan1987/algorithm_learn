package labuladong.chap03;


import labuladong.utils.TreeNode;
import org.apache.commons.lang3.StringUtils;
import java.util.*;

/**
 * @author : zhaochengming
 * @date : 2022/5/1 00:13
 * @description : 二叉树的后序遍历
 */
public class PostOrderTraverse implements ITraverse {

    // 后序遍历
    private void traverse(TreeNode<Integer> node, List<String> values) {
        if (node == null) {
            values.add(EMPTY);
            return;
        }

        traverse(node.left, values);
        traverse(node.right, values);
        values.add(String.valueOf(node.value));
    }

    @Override
    public String serialize(TreeNode<Integer> node) {
        List<String> values = new LinkedList<>();
        traverse(node, values);
        return StringUtils.join(values, SEP);
    }

    private TreeNode<Integer> deserialize(LinkedList<String> values) {
        if (values.isEmpty()) {
            return null;
        }

        String head = values.removeLast();  // 注意与前序的不同
        if (head.equals(EMPTY)) {
            return null;
        }

        TreeNode<Integer> root = new TreeNode<>(Integer.parseInt(head));

        // 注意与前序的顺序不同
        root.right = deserialize(values);
        root.left = deserialize(values);
        return root;
    }

    @Override
    public TreeNode<Integer> deserialize(String s) {
        LinkedList<String> values = new LinkedList<>(Arrays.asList(s.split(SEP)));
        return deserialize(values);
    }

    public static void main(String[] args) {

        PostOrderTraverse obj = new PostOrderTraverse();

        TreeNode<Integer> root = new TreeNode<>(1);
        root.left = new TreeNode<>(2);
        root.right = new TreeNode<>(3);
        root.left.right = new TreeNode<>(4);
        String s = obj.serialize(root);
        System.out.println(s);        // #,#,#,4,2,#,#,3,1

        TreeNode<Integer> node = obj.deserialize(s);

        BST<Integer> tree1 = new BST<>(root);
        BST<Integer> tree2 = new BST<>(node);
        System.out.println(tree1.isSameTree(tree2));        // true
    }
}
