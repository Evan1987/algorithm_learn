package leetcode;

import java.util.*;

/**
 * @author : zhaochengming
 * @date : 2022/5/15 23:57
 * @description : https://leetcode.cn/problems/capacity-to-ship-packages-within-d-days/
 */
public class L1011_CapacityToShipPackagesWithinDDays {
    static class Solution {
        public int shipWithinDays(int[] weights, int days) {
            int max = 0;
            int sum = 0;
            for (int weight: weights) {
                max = Math.max(weight, max);
                sum += weight;
            }

            int left = max;
            int right = sum;

            while (left < right) {
                int mid = left + (right - left) / 2;
                int cnt = getDays(weights, mid);
                if (cnt > days) {
                    left = mid + 1;
                } else {
                    right = mid;
                }
            }

            return left;

        }

        private int getDays(int[] weights, int load) {
            int days = 1;
            int res = load;
            for (int weight: weights) {
                if (res < weight) {
                    res = load;
                    days ++;
                }
                res -= weight;
            }

            return days;
        }
    }
}
