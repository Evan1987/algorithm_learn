package leetcode;

import java.util.*;

/**
 * @author : zhaochengming
 * @date : 2022/5/8 22:06
 * @description :
 */
public class L0020_ValidParentheses {
    static class Solution {
        private char leftOf(char s) {
            if (s == ')') return '(';
            if (s == '}') return '{';
            return '[';
        }

        public boolean isValid(String s) {
            Deque<Character> stack = new LinkedList<>();
            for (char c: s.toCharArray()) {
                if (c == ')' || c == ']' || c == '}') {
                    if (stack.isEmpty() || stack.pop() != leftOf(c))
                        return false;
                } else {
                    stack.push(c);
                }
            }
            return stack.isEmpty();
        }
    }
}
