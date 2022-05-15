package leetcode;

import java.util.*;

/**
 * @author : zhaochengming
 * @date : 2022/5/16 0:20
 * @description : https://leetcode.cn/problems/subsets/
 */
public class L0078_Subsets {
    static class Solution {

        private List<List<Integer>> res = new LinkedList<>();

        private void backTrack(int[] nums, int start, Deque<Integer> track) {

            res.add(new LinkedList<>(track));

            for (int i = start; i < nums.length; i ++) {
                track.addLast(nums[i]);
                backTrack(nums, i + 1, track);
                track.pollLast();
            }
        }

        public List<List<Integer>> subsets(int[] nums) {
            Deque<Integer> track = new LinkedList<>();
            backTrack(nums, 0, track);
            return res;
        }
    }
}
