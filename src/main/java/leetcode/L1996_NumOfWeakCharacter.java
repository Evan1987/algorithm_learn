package leetcode;

import java.util.Arrays;

/**
 * @author : zhaochengming
 * @date : 2023/6/14 19:10
 * @description : https://leetcode.cn/problems/the-number-of-weak-characters-in-the-game/
 * 游戏中弱角色数量  todo unfinished
 */
public class L1996_NumOfWeakCharacter {
    static class Solution {
        public int numberOfWeakCharacters(int[][] properties) {
            int ans = 0;
            int n = properties.length;
            // 按攻击力降序，如果相同则防御力降序
            Arrays.sort(properties, (o1, o2) -> (o2[0] - o1[0] == 0 ? o2[1] - o1[1] : o2[0] - o1[0]));
            int[] prev = properties[0];
            for (int i = 1; i < n; i ++) {
                int[] curr = properties[i];
                if (curr[0] == prev[0]) continue;
                if (curr[1] == prev[1]) {
                    continue;
                } else if (curr[1] < prev[1]) {
                    ans ++;
                } else {

                }
            }

            return ans;
        }
    }
}
