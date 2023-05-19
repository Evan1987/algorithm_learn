package leetcode;

/**
 * @author : zhaochengming
 * @date : 2023/5/19 17:11
 * @description : https://leetcode.cn/problems/letter-tile-possibilities/
 * 活字印刷
 */
public class L1079_LetterTilePossibilities {
    static class Solution {
        public int numTilePossibilities(String tiles) {
            int[] counts = new int[26];
            for (char c: tiles.toCharArray()) {
                counts[c - 'A'] ++;
            }

            return dfs(tiles.length(), counts) - 1;
        }

        private int dfs(int remain, int[] counts) {
            int res = 1;

            if (remain == 0) {
                return res;
            }

            for (int i = 0; i < counts.length; i ++) {
                if (counts[i] > 0) {
                    counts[i] -- ;
                    res += dfs(remain - 1, counts) ;
                    counts[i] ++;
                }
            }

            return res;
        }
    }
}
