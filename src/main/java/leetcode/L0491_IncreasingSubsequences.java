package leetcode;

import java.util.*;

/**
 * @author : zhaochengming
 * @date : 2022/5/8 0:17
 * @description : https://leetcode-cn.com/problems/increasing-subsequences/
 */
public class L0491_IncreasingSubsequences {
    static class Solution {

        private final List<List<Integer>> res = new LinkedList<>();
        private final LinkedList<Integer> path = new LinkedList<>();

        private void backTrack(int[] nums, int start) {

            Set<Integer> used = new HashSet<>();

            if (path.size() > 1) {
                res.add(new LinkedList<>(path));
            }

            for (int i = start; i < nums.length; i ++) {

                int x = nums[i];
                if ((!path.isEmpty() && path.getLast() > x) || used.contains(x)) {
                    continue;
                }
                path.add(x);
                used.add(x);  // 该数字已在本次root中用过，其后不可再用，防止pollLast后在之后循环时再次添加该数字
                backTrack(nums, i + 1);
                path.pollLast();
            }
        }


        public List<List<Integer>> findSubsequences(int[] nums) {
            backTrack(nums, 0);
            return res;
        }
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        int[] nums = {4,6,7,7};
        List<List<Integer>> res = sol.findSubsequences(nums);
        System.out.println(res.size());
        for (List<Integer> x: res) {
            for (int e: x) {
                System.out.print(e + " ");
            }
            System.out.println();
        }
    }
}
