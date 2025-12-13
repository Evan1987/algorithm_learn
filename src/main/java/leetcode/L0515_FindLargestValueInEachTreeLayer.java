package leetcode;


import leetcode.utils.TreeNode;
import java.util.*;

/**
 * @author : zhaochengming
 * @date : 2025/12/13 20:46
 * @description : https://leetcode.cn/problems/find-largest-value-in-each-tree-row/
 */
public class L0515_FindLargestValueInEachTreeLayer {

    // BFS
    static class Solution {
        public List<Integer> largestValues(TreeNode root) {
            List<Integer> ans = new ArrayList<>();
            if (root == null) return ans;

            Deque<TreeNode> queue = new LinkedList<>();
            queue.offer(root);
            while (!queue.isEmpty()) {
                int len = queue.size();
                int maxValue = Integer.MIN_VALUE;
                while (len > 0) {
                    TreeNode node = queue.poll();
                    len --;
                    if (node.val > maxValue) {
                        maxValue = node.val;
                    }

                    if (node.left != null) {
                        queue.offer(node.left);
                    }
                    if (node.right != null) {
                        queue.offer(node.right);
                    }
                }
                ans.add(maxValue);
            }
            return ans;
        }
    }

    // DFS
    static class Solution2 {
        private final List<Integer> ans = new ArrayList<>();
        public List<Integer> largestValues(TreeNode root) {
            if (root != null) {
                dfs(root, 0);
            }
            return ans;
        }

        private void dfs(TreeNode node, int depth) {
            if (ans.size() == depth) {
                ans.add(node.val);
            } else {
                ans.set(depth, Math.max(ans.get(depth), node.val));
            }

            if (node.left != null) {
                dfs(node.left, depth + 1);
            }

            if (node.right != null) {
                dfs(node.right, depth + 1);
            }
        }
    }
}
