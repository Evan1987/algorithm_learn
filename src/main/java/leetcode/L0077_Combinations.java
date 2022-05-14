package leetcode;

import java.util.*;

/**
 * @author : zhaochengming
 * @date : 2022/5/15 0:11
 * @description : https://leetcode.cn/problems/combinations/
 */
public class L0077_Combinations {
    static class Solution {

        private final List<List<Integer>> res = new LinkedList<>();

        private void backTrack(int n, int k, int start, Deque<Integer> track) {
            if (track.size() == k) {
                res.add(new LinkedList<>(track));
                return;
            }

            for (int i = start; i <= n - (k - track.size()) + 1; i ++) {
                track.offer(i);
                backTrack(n, k, i + 1, track);
                track.pollLast();
            }
        }

        public List<List<Integer>> combine(int n, int k) {
            Deque<Integer> track = new LinkedList<>();
            backTrack(n, k, 1, track);
            return res;
        }
    }
}
