package labuladong.utils;

/**
 * @author : zhaochengming
 * @date : 2022/4/26 17:47
 * @description :
 */
public class TreeNode<T> {

    public T value;
    public TreeNode<T> left;
    public TreeNode<T> right;

    public TreeNode(T value, TreeNode<T> left, TreeNode<T> right) {
        this.value = value;
        this.left = left;
        this.right = right;
    }

    public TreeNode(T value) {
        this(value, null, null);
    }
}
