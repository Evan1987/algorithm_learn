package labuladong.chap03;


import labuladong.utils.TreeNode;

public interface ITraverse {

    String SEP = ",";
    String EMPTY = "#";

    String serialize(TreeNode<Integer> node);

    TreeNode<Integer> deserialize(String s);
}
