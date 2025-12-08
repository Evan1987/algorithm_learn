package leetcode;

import leetcode.utils.TreeNode;

import java.util.Deque;
import java.util.LinkedList;

/*
 * @author : zhaochengming.zcm
 * @date : 2025/12/9 00:28
 * https://leetcode.cn/problems/maximum-depth-of-binary-tree
 */
public class L0104_MaxDepthOfBinaryTree {
    static class Solution {

        // 返回某节点的最大深度
        private int dfs(TreeNode node) {
            // 当节点为空则深度为0
            if (node == null) return 0;
            // 否则深度为左、右节点深度最大值 + 1
            return Math.max(dfs(node.left), dfs(node.right)) + 1;
        }

        public int maxDepth(TreeNode root) {
            return dfs(root);
        }

        static class Pair {
            public int depth;
            public TreeNode node;
            Pair(int depth, TreeNode node) {
                this.depth = depth;
                this.node = node;
            }
        }

        public int maxDepth2(TreeNode root) {
            int depth = 0;
            Deque<Pair> stack = new LinkedList<>();
            stack.addFirst(new Pair(0, root));
            while (!stack.isEmpty()) {
                Pair pair = stack.pollFirst();
                if (pair.node == null) {
                    depth = Math.max(depth, pair.depth);
                } else {
                    stack.addFirst(new Pair(pair.depth + 1, pair.node.left));
                    stack.addFirst(new Pair(pair.depth + 1, pair.node.right));
                }
            }
            return depth;
        }
    }
}
