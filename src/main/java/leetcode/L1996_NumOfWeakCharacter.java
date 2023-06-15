package leetcode;

import java.util.Arrays;

/**
 * @author : zhaochengming
 * @date : 2023/6/14 19:10
 * @description : https://leetcode.cn/problems/the-number-of-weak-characters-in-the-game/
 * 游戏中弱角色数量
 */
public class L1996_NumOfWeakCharacter {
    static class Solution {
        public int numberOfWeakCharacters(int[][] properties) {
            int ans = 0;
            int n = properties.length;
            // 按攻击力降序，如果相同则防御力升序
            Arrays.sort(properties, (o1, o2) -> (o2[0] - o1[0] == 0 ? o1[1] - o2[1] : o2[0] - o1[0]));
            int maxDef = 0;
            for (int[] p: properties) {
                if (p[1] >= maxDef) {
                    maxDef = p[1];
                } else {
                    // p[1] < maxDef 由于在攻击力一样的情况下，防御力是升序的，说明这个maxDef一定是在不一样的攻击力分组中获得的。
                    // 即一定有攻击力、防御力都比p大的元素
                    ans ++;
                }
            }
            return ans;
        }
    }
}
