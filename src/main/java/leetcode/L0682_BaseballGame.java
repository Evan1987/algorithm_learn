package leetcode;


/**
 * @author : zhaochengming
 * @date : 2022/5/17 18:24
 * @description : https://leetcode.cn/problems/baseball-game/
 */
public class L0682_BaseballGame {
    static class Solution {
        public int calPoints(String[] ops) {
            int n = ops.length;
            int[] res = new int[n];
            int curr = 0;
            for (String op: ops) {
                switch (op) {
                    case "+":
                        res[curr] = res[curr - 1] + res[curr - 2];
                        curr ++;
                        break;
                    case "D":
                        res[curr] = 2 * res[curr - 1];
                        curr ++;
                        break;
                    case "C":
                        res[--curr] = 0;
                        break;
                    default:
                        res[curr ++] = Integer.parseInt(op);
                }
            }

            int ans = 0;
            for (int i = 0; i < curr; i ++) {
                ans += res[i];
            }

            return ans;

        }
    }
}
