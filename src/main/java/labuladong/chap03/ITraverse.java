package labuladong.chap03;


import labuladong.utils.TreeNode;

public interface ITraverse {

    String serialize(TreeNode<Integer> node);

    TreeNode<Integer> deserialize(String s);
}
