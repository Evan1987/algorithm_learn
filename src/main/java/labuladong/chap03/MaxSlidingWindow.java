package labuladong.chap03;

import labuladong.utils.MonotonicQueue;

import java.util.PriorityQueue;

/**
 * @author : zhaochengming
 * @date : 2022/5/3 0:26
 * @description : 窗口 K内最大元素值
 */
public class MaxSlidingWindow {

    public static int[] solve1(int[] nums, int k) {
        int n = nums.length;
        int[] res = new int[n - k + 1];
        // 大顶堆
        PriorityQueue<Integer> q = new PriorityQueue<>(k, (o1, o2) -> -o1.compareTo(o2));
        // 初始化
        for (int i = 0; i < k; i ++) {
            q.offer(nums[i]);
        }
        res[0] = q.peek();

        for (int i = k; i < n; i ++) {
            q.remove(nums[i - k]);
            q.offer(nums[i]);
            res[i - k + 1] = q.peek();
        }

        return res;
    }

    public static int[] solve2(int[] nums, int k) {
        int n = nums.length;
        int[] res = new int[n - k + 1];
        // 单调队列
        MonotonicQueue<Integer> q = new MonotonicQueue<>();
        // 初始化
        for (int i = 0; i < k; i ++) {
            q.push(nums[i]);
        }
        res[0] = q.max();

        for (int i = k; i < n; i ++) {
            if (nums[i - k] == q.max()) {
                q.pop();
            }
            q.push(nums[i]);
            res[i - k + 1] = q.max();
        }

        return res;
    }

    public static void main(String[] args) {
        int[] nums = {1, 3, -1, -3, 5, 3, 6, 7};
        int k = 3;
        // [3, 3, 5, 5, 6, 7]
        for (int x: solve1(nums, k)) {
            System.out.println(x);
        }

        // [3, 3, 5, 5, 6, 7]
        for (int x: solve2(nums, k)) {
            System.out.println(x);
        }
    }
}
