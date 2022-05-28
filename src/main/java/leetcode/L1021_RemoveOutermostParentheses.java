package leetcode;


/**
 * @author : zhaochengming
 * @date : 2022/5/28 13:23
 * @description : https://leetcode.cn/problems/remove-outermost-parentheses/
 */
public class L1021_RemoveOutermostParentheses {

    static class Solution {
        public String removeOuterParentheses(String s) {
            if (s.equals("")) return "";

            StringBuilder sb = new StringBuilder();

            int left = 0;
            for (char c: s.toCharArray()) {
                if (c == '(') {
                    if (left > 0) sb.append(c);
                    left ++;
                } else {
                    left --;
                    if (left > 0) sb.append(c);
                }

            }

            return sb.toString();
        }
    }
}
