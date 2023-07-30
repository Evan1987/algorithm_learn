package leetcode;


import java.util.*;

/**
 * @author : zhaochengming
 * @date : 2023/7/30 08:09
 * @description : https://leetcode.cn/problems/jump-game-vi/
 * 跳跃游戏，最多跳k步，最大得分
 */
public class L1696_JumpGame6 {
    static class Solution {
        // Fi = max(Fj) + nums[i], (i - k) <= j < i

        // dp + 单调队列优化 (在插入数据时保证单调)
        public int maxResult(int[] nums, int k) {
            int n = nums.length;

            // 从队首到队尾，下标严格递增，F值严格递减
            Deque<int[]> q = new LinkedList<>();
            q.offerLast(new int[]{0, nums[0]});
            int ans = nums[0];

            for (int i = 1; i < n; i ++) {

                // 队首不满足range要求直接剔除，对于今后的运算没有帮助
                while (i - q.peekFirst()[0] > k) {
                    q.pollFirst();
                }

                int[] info = q.peekFirst();
                ans = info[1] + nums[i];

                // 按道理应该即将把[i, ans]插入到队尾（保证下标严格递增），但是为了保证值的单调性，需要检查队尾的值
                while (!q.isEmpty() && q.peekLast()[1] < ans) {
                    q.pollLast();
                }

                q.offerLast(new int[]{i, ans});

            }

            return ans;

        }

        // dp + 优先队列优化 优先队列用于求max(Fj) (i - k) <= j < i
        public int maxResult2(int[] nums, int k) {
            int n = nums.length;

            // [index, F(index)]
            PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(o -> -o[1]));
            pq.offer(new int[]{0, nums[0]});
            int ans = nums[0];

            for (int i = 1; i < n; i ++) {
                while (i - pq.peek()[0] > k) {
                    pq.poll();
                }

                int[] info = pq.peek();
                ans = nums[i] + info[1];
                pq.offer(new int[]{i, ans});
            }

            return ans;
        }
    }

}
