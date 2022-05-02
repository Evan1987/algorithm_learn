package labuladong.chap03;

import java.util.*;

/**
 * @author : zhaochengming
 * @date : 2022/5/2 8:05
 * @description : 单调栈
 */
public class NextGreaterElement {

    public static int[] solve1(int[] nums) {
        int n = nums.length;
        int[] res = new int[n];
        Deque<Integer> stack = new LinkedList<>();

        for (int i = n - 1; i >= 0; i --) {
            while (!stack.isEmpty() && stack.peek() <= nums[i]) {
                stack.pop();
            }

            res[i] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(nums[i]);
        }

        return res;
    }

    // 环形数组, 通过使数组翻倍达到效果
    public static int[] solve2(int[] nums) {
        int n = nums.length;
        int[] res = new int[n];
        Deque<Integer> stack = new LinkedList<>();

        // 假装翻倍
        for (int i = 2 * n - 1; i >= 0; i --) {
            while (!stack.isEmpty() && stack.peek() <= nums[i % n]) {
                stack.pop();
            }

            res[i % n] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(nums[i % n]);
        }
        return res;
    }

    public static void main(String[] args) {
        int[] nums = {2, 1, 2, 4, 3};

        // [4, 2, 4, -1, -1]
        for (int x: solve1(nums)) {
            System.out.println(x);
        }

        // [4, 2, 4, -1, 4]
        for (int x: solve2(nums)) {
            System.out.println(x);
        }
    }
}
