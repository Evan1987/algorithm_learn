package leetcode;

/**
 * @author : zhaochengming
 * @date : 2023/6/15 19:52
 * @description : https://leetcode.cn/problems/determine-the-winner-of-a-bowling-game/
 * 保龄球游戏的胜利者
 */
public class L2660_WinnerOfBowlingGame {
    static class Solution {
        public int isWinner(int[] player1, int[] player2) {
            int score1 = bowlingScore(player1);
            int score2 = bowlingScore(player2);
            if (score1 == score2) {
                return 0;
            }
            return score1 > score2 ? 1 : 2;
        }

        private static int bowlingScore(int[] records) {
            int score = 0;
            int flag = 0;
            for (int x: records) {
                score += flag > 0 ? x * 2 : x;
                if (x == 10) {
                    flag = 2;
                } else if (flag > 0) {
                    flag --;
                }
            }
            return score;
        }
    }
}
