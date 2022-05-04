package labuladong.chap04;

import utils.annotations.WatchTime;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * @author : zhaochengming
 * @date : 2022/5/4 15:57
 * @description : 求子集问题
 */
public class Subset {

    private static void print(List<List<Integer>> subsets) {
        for (List<Integer> subset: subsets) {
            for (int x: subset) {
                System.out.print(x + " ");
            }
            System.out.println();
        }
    }

    private static final List<List<Integer>> res = new LinkedList<>();

    private static void backTrack(int[] nums, int start, Deque<Integer> track) {
        // 记录节点
        res.add(new LinkedList<>(track));
        for (int i = start; i < nums.length; i ++) {
            track.offer(nums[i]);
            backTrack(nums, i + 1, track);
            track.pollLast();
        }
    }

    @WatchTime(methodDesc = "backTrack")
    public static List<List<Integer>> solve1(int[] nums) {
        Deque<Integer> track = new LinkedList<>();
        backTrack(nums, 0, track);
        return res;
    }


    public static void main(String[] args) {
        int[] nums = {1, 2, 3};
        System.out.println(solve1(nums));
    }

}
