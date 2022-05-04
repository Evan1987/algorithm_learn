package labuladong.chap04;

import java.util.*;

/**
 * @author : zhaochengming
 * @date : 2022/5/4 16:22
 * @description : 组合, nums 数组中 k个数字的组合
 */
public class Combination {

    private static final List<List<Integer>> res = new LinkedList<>();

    private static void backTrack(int[] nums, int k, int start, Deque<Integer> track) {
        // 与取所有子集的区别
        if (track.size() == k) {
            res.add(new ArrayList<>(track));
            return;
        }

        for (int i = start; i < nums.length; i ++) {
            track.offer(nums[i]);
            backTrack(nums, k, i + 1, track);
            track.pollLast();
        }
    }

    public static List<List<Integer>> solve(int[] nums, int k) {
        Deque<Integer> track = new LinkedList<>();
        backTrack(nums, k, 0, track);
        return res;
    }

    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 4};
        int k = 2;
        for (List<Integer> subset: solve(nums, k)) {
            for (int x: subset) {
                System.out.print(x + " ");
            }
            System.out.println();
        }
    }
}
