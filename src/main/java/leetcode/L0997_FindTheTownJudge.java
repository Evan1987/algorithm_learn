package leetcode;

/**
 * @author : zhaochengming
 * @date : 2022/5/8 20:51
 * @description : https://leetcode-cn.com/problems/find-the-town-judge/
 */
public class L0997_FindTheTownJudge {
    static class Solution {
        public int findJudge(int n, int[][] trust) {
            int[] in = new int[n + 1];
            int[] out = new int[n + 1];

            for (int[] t: trust) {
                in[t[1]] ++;
                out[t[0]] ++;
            }

            for (int i = 1; i <= n; i ++) {
                if (in[i] == n - 1 && out[i] == 0) {
                    return i;
                }
            }
            return -1;
        }
    }
}
