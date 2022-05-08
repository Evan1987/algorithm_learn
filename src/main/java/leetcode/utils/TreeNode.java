package leetcode.utils;

/**
 * @author : zhaochengming
 * @date : 2022/5/8 20:07
 * @description :
 */
public class TreeNode {
    public int val;
    public TreeNode left;
    public TreeNode right;

    public TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }


    public TreeNode(int val) {
        this(val, null, null);
    }

    public TreeNode() {
        this(0);
    }
}
