package leetcode;


/**
 * @author : zhaochengming
 * @date : 2022/5/21 23:16
 * @description : https://leetcode.cn/problems/best-time-to-buy-and-sell-stock/
 */
public class L0121_BestTimeToBuyAndSell {
    static class Solution {
        public int maxProfit(int[] prices) {
            int n = prices.length;
            if (n == 1) return 0;
            int min = prices[0];
            int ans = 0;
            for (int i = 1; i < n; i ++) {
//                ans = Math.max(ans, prices[i] - min);
//                min = Math.min(min, prices[i]);

                if (prices[i] < min) {
                    min = prices[i];
                } else {
                    ans = Math.max(ans, prices[i] - min);
                }
            }

            return ans;
        }
    }
}
