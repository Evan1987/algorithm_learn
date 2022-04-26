package labuladong.chap02;


import labuladong.utils.TreeNode;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : zhaochengming
 * @date : 2022/4/26 17:17
 * @description : 相邻房子的钱不能取出
 */
public class Rob {

    private static int solve(int[] nums, int start, int end) {
        if (end < start) return 0;
        int n = end - start + 1;
        int[] dp = new int[n + 1];
        dp[0] = 0;
        dp[1] = nums[start];
        for (int i = 2; i <= n; i ++) {
            dp[i] = Math.max(dp[i - 1], dp[i - 2] + nums[i - 1 + start]);
        }
        return dp[n];
    }

    // 一维排列问题
    public static int lineSolve(int[] nums) {
        return solve(nums, 0, nums.length - 1);
    }

    // 环状，意味着首尾不能同时取
    public static int rangeSolve(int[] nums) {
        int n = nums.length;
        return Math.max(solve(nums, 0, n - 2), solve(nums, 1, n - 1));
    }

    // 二叉树，直接相连房子不能取
    public static int treeSolve(TreeNode<Integer> root) {
        Map<TreeNode<Integer>, Integer> memo = new HashMap<>();
        return treeSolve(root, memo);
    }

    private static int treeSolve(TreeNode<Integer> node, Map<TreeNode<Integer>, Integer> memo) {
        if (node == null) return 0;
        Integer val = memo.get(node);
        if (val != null) {
            return val;
        }

        int rob = node.value + (node.left == null ? 0 : treeSolve(node.left.left, memo) + treeSolve(node.left.right, memo)) +
                (node.right == null ? 0 : treeSolve(node.right.left, memo) + treeSolve(node.right.right, memo));

        int noRob = treeSolve(node.left) + treeSolve(node.right);
        int res = Math.max(rob, noRob);
        memo.put(node, res);
        return res;
    }

    private static TreeNode<Integer> generateTree() {
        TreeNode<Integer> root = new TreeNode<>(3);
        TreeNode<Integer> f1l = new TreeNode<>(4);
        TreeNode<Integer> f1r = new TreeNode<>(5);
        root.left = f1l;
        root.right = f1r;
        f1l.left = new TreeNode<>(1);
        f1l.right = new TreeNode<>(3);
        f1r.right = new TreeNode<>(1);
        return root;
    }

    public static void main(String[] args) {
        int[] nums = {2, 1, 7, 9, 3, 1};
        System.out.println(lineSolve(nums));

        int[] nums2 = {2, 3, 2};
        System.out.println(rangeSolve(nums2));

        System.out.println(treeSolve(generateTree()));

    }
}
