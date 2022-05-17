package leetcode;

import java.util.*;

/**
 * @author : zhaochengming
 * @date : 2022/5/17 18:03
 * @description : https://leetcode.cn/problems/minimum-number-of-people-to-teach/
 */
public class L1733_MinimumNumOfPeopleToTeach {
    static class Solution {
        public int minimumTeachings(int n, int[][] languages, int[][] friendships) {

            Set<Integer> noConnects = new HashSet<>();
            for (int[] friendship: friendships) {
                if (!isConnect(languages[friendship[0] - 1], languages[friendship[1] - 1])) {
                    noConnects.add(friendship[0]);
                    noConnects.add(friendship[1]);
                }
            }

            // 统计不联通用户的最大覆盖语言
            int[] coverage = new int[n + 1];
            for (int p: noConnects) {
                for (int language: languages[p - 1]) {
                    coverage[language] ++;
                }
            }

            int max = 0;
            for (int c: coverage) {
                max = Math.max(max, c);
            }

            return noConnects.size() - max;
        }

        private boolean isConnect(int[] p1, int [] p2) {
            for (int a: p1) {
                for (int b: p2) {
                    if (a == b) return true;
                }
            }
            return false;
        }
    }
}
