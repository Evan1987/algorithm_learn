package labuladong.chap05;

import java.util.Deque;
import java.util.LinkedList;

/**
 * @author : zhaochengming
 * @date : 2022/5/6 14:55
 * @description :
 */
public class ParenthesesValid {

    // 只有圆括号
    public static boolean check1(String s) {
        int left = 0;
        for (char c: s.toCharArray()) {
            if (c == '(') {
                left ++;
            } else if (c == ')') {
                left --;
            }

            if (left < 0) {
                return false;
            }
        }

        return true;
    }


    private static char leftOf(char c) {
        switch (c) {
            case ']': return '[';
            case '}': return '{';
            default: return '(';
        }
    }


    // 三种括号 () [] {}
    public static boolean check2(String s) {
        Deque<Character> stack = new LinkedList<>();

        for (char c: s.toCharArray()) {
            // 左括号入栈
            if (c == '(' || c == '[' || c == '{') {
                stack.push(c);
            } else {
                if (stack.isEmpty() || leftOf(c) != stack.pop()) {
                    return false;
                }
            }
        }

        return stack.isEmpty();
    }

    public static void main(String[] args) {
        String s1 = "()()()";
        String s2 = "([)]{}";
        System.out.println(check1(s1));
        System.out.println(check2(s1));
        System.out.println(check2(s2));
    }


}
