package leetcode;

import java.util.*;

/**
 * @author : zhaochengming
 * @date : 2023/7/29 14:07
 * @description : https://leetcode.cn/problems/maximum-depth-of-n-ary-tree/
 * N叉树的最大深度
 */
public class L0559_MaxDepthOfNAryTree {
    class Node {
        public int val;
        public List<Node> children;

        public Node() {}

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, List<Node> _children) {
            val = _val;
            children = _children;
        }
    }

    static class Solution {

        // 广度优先
        public int maxDepth2(Node root) {
            int ans = 0;
            if (root == null) return ans;
            Deque<Node> q = new LinkedList<>();
            q.offer(root);

            while (!q.isEmpty()) {
                // 把同一层的节点都poll出来
                int size = q.size();
                while (size > 0) {
                    Node node = q.poll();
                    if (node != null && node.children != null) {
                        for (Node child: node.children) {
                            q.offer(child);
                        }
                    }

                    size --;
                }

                ans ++;
            }
            return ans;

        }

        // 深度优先
        public int maxDepth(Node root) {
            if (root == null) {
                return 0;
            }

            int depth = 0;
            if (root.children != null) {
                for (Node node: root.children) {
                    depth = Math.max(maxDepth(node), depth);
                }
            }

            return depth + 1;
        }
    }
}
