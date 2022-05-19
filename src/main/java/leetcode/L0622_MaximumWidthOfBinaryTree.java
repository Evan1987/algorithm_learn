package leetcode;

import leetcode.utils.TreeNode;

import java.util.*;

/**
 * @author : zhaochengming
 * @date : 2022/5/18 11:04
 * @description : https://leetcode.cn/problems/maximum-width-of-binary-tree/
 */
public class L0622_MaximumWidthOfBinaryTree {

    static class Solution {

        private static class Tuple<L, R> {
            L left;
            R right;

            Tuple(L left, R right) {
                this.left = left;
                this.right = right;
            }

            static <L, R> Tuple<L, R> of(L left, R right) {
                return new Tuple<>(left, right);
            }
        }

        public int widthOfBinaryTree(TreeNode root) {
            if (root == null) return 0;
            int ans = 0;
            Deque<Tuple<TreeNode, Integer>> stack = new LinkedList<>();
            stack.offerLast(Tuple.of(root, 0));

            while (!stack.isEmpty()) {
                int size = stack.size();
                int width = stack.peekLast().right - stack.peekFirst().right + 1;
                ans = Math.max(ans, width);

                for (int i = 0; i < size; i ++) {
                    Tuple<TreeNode, Integer> pair = stack.pollFirst();
                    TreeNode node = pair.left;
                    int seq = pair.right;
                    seq <<= 1;
                    if (node.left != null) {
                        stack.offerLast(Tuple.of(node.left, seq));
                    }

                    if (node.right != null) {
                        stack.offerLast(Tuple.of(node.right, seq + 1));
                    }
                }
            }

            return ans;
        }
    }
}
