package leetcode;


/**
 * @author : zhaochengming
 * @date : 2022/5/25 18:27
 * @description : https://leetcode.cn/problems/living-people-lcci/
 */
public class MST1610_LivingPeople {
    static class Solution {
        public int maxAliveYear(int[] birth, int[] death) {

            // years[0] -> 1900 years[100] -> 2000
            int[] years = new int[101];
            for (int y: birth) {
                years[y - 1900] ++;
            }

            for (int y: death) {
                if (y >= 2000) continue;
                years[y - 1900 + 1] --;
            }

            int num = 0, max = 0, ans = 0;
            for (int i = 0; i < years.length; i ++) {
                if (years[i] == 0) continue;
                num += years[i];
                if (years[i] > 0 && num > max) {
                   max = num;
                   ans = i;
                }
            }

            return 1900 + ans;

        }
    }
}
