package labuladong.chap03;

import java.util.Deque;
import java.util.LinkedList;

/**
 * @author : zhaochengming
 * @date : 2022/5/3 0:10
 * @description :
 */
public class NextWarmerTemperature {

    public static int[] solve(int[] temperatures) {
        int n = temperatures.length;
        int[] res = new int[n];
        Deque<Integer> s = new LinkedList<>();   // 存索引
        for (int i = n - 1; i >= 0; i --) {
            while (!s.isEmpty() && temperatures[s.peek()] <= temperatures[i]) {
                s.pop();
            }

            res[i] = s.isEmpty() ? 0 : s.peek() - i;
            s.push(i);
        }
        return res;
    }

    public static void main(String[] args) {
        int[] temperatures = {73, 74, 75, 71, 69, 72, 76, 73};
        for (int x: solve(temperatures)) {
            System.out.println(x);
        }
    }
}
