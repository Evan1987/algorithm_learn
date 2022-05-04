package labuladong.chap04;

import java.util.*;

/**
 * @author : zhaochengming
 * @date : 2022/5/4 22:58
 * @description : 计算器，处理整数四则运算和括号，忽略空格
 */
public class Calculator {

    private static double calculate(List<Character> s) {
        Deque<Double> stack = new LinkedList<>();
        char sign = '+';
        double num = 0;

        while (s.size() > 0) {
            char c = s.remove(0);
            if (Character.isDigit(c)) {
                num = 10 * num + (c - '0');
            }

            if (c == '(') {
                num = calculate(s);
            }

            // 要入栈
            if ((!Character.isDigit(c) && c != ' ') || s.size() == 0) {
                switch (sign) {
                    case '+':
                        stack.push(num); break;
                    case '-':
                        stack.push(-num); break;
                    case '*':
                        stack.push(stack.pop() * num); break;
                    case '/':
                        stack.push(stack.pop() / num); break;
                }

                num = 0;
                sign = c;
            }

            // 注意位置
            if (c == ')') {
                break;
            }
        }

        double sum = 0;
        for (double x: stack) {
            sum += x;
        }

        return sum;
    }

    public static double solve(String input) {
        List<Character> q = new LinkedList<>();
        for (Character x: input.toCharArray()) {
            q.add(x);
        }

        return calculate(q);
    }


    public static void main(String[] args) {
        String input = "3 * (4 - 5 / 2) - 6";
        System.out.println(solve(input));
    }

}
