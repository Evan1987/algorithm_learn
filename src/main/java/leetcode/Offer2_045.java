package leetcode;

import leetcode.utils.TreeNode;
import java.util.*;

/**
 * @author : zhaochengming
 * @date : 2023/5/5 15:44
 * @description : https://leetcode.cn/problems/LwUNpT/
 * 二叉树最底层最左边节点的值
 */
public class Offer2_045 {
    static class Solution {
        public int findBottomLeftValue(TreeNode root) {
            int ans = root.val;

            Deque<TreeNode> q = new LinkedList<>();
            q.offer(root);
            while (!q.isEmpty()) {
                TreeNode node = q.poll();
                if (node.right != null) {
                    q.offer(node.right);
                }
                if (node.left != null) {
                    q.offer(node.left);
                }

                ans = node.val;
            }
            return ans;
        }
    }
}
