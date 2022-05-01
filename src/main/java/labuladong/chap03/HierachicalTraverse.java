package labuladong.chap03;

import labuladong.utils.TreeNode;
import org.apache.commons.lang3.StringUtils;
import java.util.*;

/**
 * @author : zhaochengming
 * @date : 2022/5/1 21:25
 * @description :
 */
public class HierachicalTraverse implements ITraverse {

    @Override
    public String serialize(TreeNode<Integer> node) {
        Queue<TreeNode<Integer>> q = new LinkedList<>();
        List<String> values = new LinkedList<>();

        q.offer(node);

        // BFS
        while (!q.isEmpty()) {
            TreeNode<Integer> n = q.poll();   // remove head
            if (n == null) {
                values.add(EMPTY);
                continue;
            }

            values.add(String.valueOf(n.value));
            q.offer(n.left);
            q.offer(n.right);
        }

        return StringUtils.join(values, SEP);
    }

    @Override
    public TreeNode<Integer> deserialize(String s) {
        if (StringUtils.isEmpty(s)) {
            return null;
        }
        String[] nodes = s.split(SEP);

        TreeNode<Integer> root = new TreeNode<>(Integer.parseInt(nodes[0]));

        Queue<TreeNode<Integer>> q = new LinkedList<>();
        q.offer(root);

        int i = 1;
        while (i < nodes.length) {
            TreeNode<Integer> parent = q.poll();

            String left = nodes[i ++];
            if (left.equals(EMPTY)) {
                parent.left = null;
            } else {
                parent.left = new TreeNode<>(Integer.parseInt(left));
                q.offer(parent.left);
            }

            String right = nodes[i ++];
            if (right.equals(EMPTY)) {
                parent.right = null;
            } else {
                parent.right = new TreeNode<>(Integer.parseInt(right));
                q.offer(parent.right);
            }
        }

        return root;
    }

    public static void main(String[] args) {
        HierachicalTraverse obj = new HierachicalTraverse();

        TreeNode<Integer> root = new TreeNode<>(1);
        root.left = new TreeNode<>(2);
        root.right = new TreeNode<>(3);
        root.left.right = new TreeNode<>(4);
        String s = obj.serialize(root);
        System.out.println(s);        // 1,2,3,#,4,#,#,#,#

        TreeNode<Integer> node = obj.deserialize(s);

        BST<Integer> tree1 = new BST<>(root);
        BST<Integer> tree2 = new BST<>(node);
        System.out.println(tree1.isSameTree(tree2));        // true
    }
}
