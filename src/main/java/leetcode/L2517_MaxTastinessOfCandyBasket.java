package leetcode;

import java.util.Arrays;

/**
 * @author : zhaochengming
 * @date : 2023/6/1 11:04
 * @description : https://leetcode.cn/problems/maximum-tastiness-of-candy-basket/
 * 糖果礼盒的最大甜蜜度
 */
public class L2517_MaxTastinessOfCandyBasket {
    static class Solution {
        public int maximumTastiness(int[] price, int k) {
            Arrays.sort(price);
            int n = price.length;
            if (k == 2) return price[n - 1] - price[0];
            if (price[n - 1] == price[0]) return 0;

            int left = Integer.MAX_VALUE;
            for (int i = 1; i < n; i ++) {
                left = Math.min(left, price[i] - price[i - 1]);
            }

            int right = price[n - 1] - price[0];
            while (left < right) {
                int mid = (right + left + 1) / 2;   // 向上取证，否则left=mid时可能会导致死循环
                if (check(price, k, mid)) {
                    left = mid;
                } else {
                    right = mid - 1;
                }
            }
            return left;

        }

        private static boolean check(int[] price, int k, int tastiness) {
            int count = 0;
            int prev = Integer.MIN_VALUE / 2;
            for (int p: price) {
                if (p - prev >= tastiness) {
                    count ++;
                    if (count >= k) return true;
                    prev = p;
                }
            }
            return false;
        }
    }
}
