package leetcode;

import java.util.*;

/**
 * @author : zhaochengming
 * @date : 2022/5/21 15:27
 * @description : https://leetcode.cn/problems/minimize-the-difference-between-target-and-chosen-elements/
 */
public class L1981_MinDiffBetweenTargetAndChosenElements {

    static class Solution {

        private int MIN_VALUE = Integer.MAX_VALUE;

        public int minimizeTheDifference(int[][] mat, int target) {
            Map<Integer, Set<Integer>> cumSumChosen = new HashMap<>();
            backTrack(mat, 0, 0, target, cumSumChosen);
            return MIN_VALUE;
        }

        private void backTrack(int[][] mat, int start, int sum, int target, Map<Integer, Set<Integer>> cumSumChosen) {
            if (start == mat.length) {
                MIN_VALUE = Math.min(MIN_VALUE, Math.abs(sum - target));
                return;
            }

            cumSumChosen.putIfAbsent(start, new HashSet<>());
            Set<Integer> chosen = cumSumChosen.get(start);
            int minHi = Integer.MAX_VALUE;
            for (int x: mat[start]) {
                int s = sum + x;
                if (!chosen.add(s)) {
                    continue;
                }
                if (s < target) {
                    backTrack(mat, start + 1, s, target, cumSumChosen);
                } else {
                    minHi = Math.min(minHi, s);
                }
            }

            if (minHi < Integer.MAX_VALUE) {
                backTrack(mat, start + 1, minHi, target, cumSumChosen);
            }

        }
    }
}
