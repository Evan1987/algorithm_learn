package leetcode;

/**
 * @author : zhaochengming
 * @date : 2023/5/7 10:43
 * @description : https://leetcode.cn/problems/guess-numbers/
 * 猜数字
 */
public class LCP01_GuessNumber {
    static class Solution {
        public int game(int[] guess, int[] answer) {
            int ans = 0;
            for (int i = 0; i < guess.length; i ++) {
                if (guess[i] == answer[i]) {
                    ans ++;
                }
            }
            return ans;
        }
    }
}
