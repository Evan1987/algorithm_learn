package leetcode;

import leetcode.utils.TreeNode;

import java.util.*;

/**
 * @author : zhaochengming
 * @date : 2022/5/19 11:46
 * @description : https://leetcode.cn/problems/binary-tree-level-order-traversal/
 */
public class L0102_BinaryTreeLevelOrderTraversal {
    static class Solution {
        public List<List<Integer>> levelOrder(TreeNode root) {
            List<List<Integer>> ans = new LinkedList<>();
            if (root == null) return ans;

            Deque<TreeNode> queue = new LinkedList<>();
            queue.add(root);

            while (!queue.isEmpty()) {
                List<Integer> temp = new LinkedList<>();
                int size = queue.size();
                for (int i = 0; i < size; i ++) {
                    TreeNode node = queue.pollFirst();
                    if (node == null) continue;
                    temp.add(node.val);
                    queue.offerLast(node.left);
                    queue.offerLast(node.right);
                }

                if (!temp.isEmpty()) {
                    ans.add(temp);
                }
            }

            return ans;

        }
    }
}
