package leetcode;

import leetcode.utils.TreeNode;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * @author : zhaochengming
 * @date : 2023/8/2 16:26
 * @description : https://leetcode.cn/problems/lowest-common-ancestor-of-a-binary-tree/
 * 二叉树给定节点的公共祖先
 */
public class L0236_LowestCommonAncestor {

    static class Solution {

        TreeNode ans = null;

        public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
            if (root.val == p.val || root.val == q.val) return root;
            dfs(root, p, q);
            return ans;
        }

        // node及其子树中是否包含p 或 q
        private boolean dfs(TreeNode node, TreeNode p, TreeNode q) {
            if (node == null) return false;
            boolean inLeft = dfs(node.left, p, q);
            boolean inRight = dfs(node.right, p, q);
            if ((inLeft && inRight) || ((node.val == p.val || node.val == q.val) && (inLeft || inRight))) {
                ans = node;
            }

            return inLeft || inRight || node.val == p.val || node.val == q.val;
        }
    }


    static class Solution1 {
        // 二进制编码
        Map<String, TreeNode> codeNodeMap = new HashMap<>();
        Map<TreeNode, String> nodeCodeMap = new HashMap<>();

        public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
            if (root.val == p.val || root.val == q.val) return root;
            codeNode(root, "");

            String code1 = nodeCodeMap.get(p);
            String code2 = nodeCodeMap.get(q);

            int i = 0;
            while (i < code1.length() && i < code2.length() && code1.charAt(i) == code2.charAt(i)) {
                i ++;
            }

            if (i == 0) return root;
            return codeNodeMap.get(code1.substring(0, i));
        }

        private void codeNode(TreeNode node, String code) {
            codeNodeMap.put(code, node);
            nodeCodeMap.put(node, code);

            if (node.left != null) {
                codeNode(node.left, code + "0");
            }

            if (node.right != null) {
                codeNode(node.right, code + "1");
            }
        }
    }
}
