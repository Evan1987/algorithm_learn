package leetcode;

/**
 * @author : zhaochengming
 * @date : 2023/4/24 19:16
 * @description : https://leetcode.cn/problems/minimum-penalty-for-a-shop/
 */
public class L2483_MinPenaltyForShop {
    static class Solution {
        public int bestClosingTime(String customers) {
            int n = customers.length();
            int cost = 0;
            for (char c: customers.toCharArray()) {
                if (c == 'Y') cost ++;
            }

            if (cost == n) {
                return n;
            }

            if (cost == 0) {
                return 0;
            }

            int ans = 0;
            int best = cost;

            for (int i = 1; i <= n; i ++) {
                if (customers.charAt(i - 1) == 'Y') {
                    cost --;
                    if (cost < best) {
                        best = cost;
                        ans = i;
                    }

                } else {
                    cost ++;
                }
            }

            return ans;
        }
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        String[] customers = {"YYNY", "NNNNN", "YYYY"};
        for (String customer: customers) {
            System.out.println(customer + ": " + sol.bestClosingTime(customer));
        }
    }
}
