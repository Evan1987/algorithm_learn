package leetcode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author : zhaochengming
 * @date : 2023/8/3 15:26
 * @description : https://leetcode.cn/problems/permutations/
 * 全排列
 */
public class L0046_Permutations {
    static class Solution {
        List<List<Integer>> ans = new LinkedList<>();

        public List<List<Integer>> permute(int[] nums) {
            List<Integer> numsList = new ArrayList<>();
            for (int num: nums) {
                numsList.add(num);
            }

            backTrack(numsList, new LinkedList<>());
            return ans;
        }

        private void backTrack(List<Integer> nums, List<Integer> path) {
            if (nums.isEmpty()) {
                ans.add(path);
                return;
            }

            int size = nums.size();
            for (int i = 0; i < size; i ++) {
                List<Integer> newPath = new LinkedList<>(path);
                newPath.add(nums.get(i));
                List<Integer> newNums = new ArrayList<>(nums);
                newNums.remove(i);
                backTrack(newNums, newPath);
            }
        }
    }
}
