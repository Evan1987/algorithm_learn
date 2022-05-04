package labuladong.chap04;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * @author : zhaochengming
 * @date : 2022/5/4 16:36
 * @description : 排列
 */
public class Permutation {

    private static final List<List<Integer>> res = new LinkedList<>();

    private static void backTrack(int[] nums, Deque<Integer> track) {
        if (track.size() == nums.length) {
            res.add(new ArrayList<>(track));
            return;
        }

        for (int num: nums) {
            // 剪枝
            if (track.contains(num)) {
                continue;
            }
            track.offer(num);
            backTrack(nums, track);
            track.pollLast();
        }


    }

    public static List<List<Integer>> solve(int[] nums) {
        Deque<Integer> track = new LinkedList<>();
        backTrack(nums, track);
        return res;
    }

    public static void main(String[] args) {
        int[] nums = {1, 2, 3};
        for (List<Integer> subset: solve(nums)) {
            for (int x: subset) {
                System.out.print(x + " ");
            }
            System.out.println();
        }
    }
}
