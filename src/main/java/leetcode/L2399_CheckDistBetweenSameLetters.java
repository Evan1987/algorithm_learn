package leetcode;

import java.util.Arrays;

/**
 * @author : zhaochengming
 * @date : 2023/6/15 17:15
 * @description : https://leetcode.cn/problems/check-distances-between-same-letters/
 * 检查相同字母的距离
 */
public class L2399_CheckDistBetweenSameLetters {
    static class Solution {
        public boolean checkDistances(String s, int[] distance) {
            int[] pos = new int[26];
            Arrays.fill(pos, -1);

            for (int i = 0; i < s.length(); i ++) {
                int index = s.charAt(i) - 'a';
                if (pos[index] == -1) {
                    pos[index] = i;
                } else {
                    int d = i - pos[index] - 1;
                    if (d != distance[index]) {
                        return false;
                    }
                }
            }
            return true;
        }
    }
}
