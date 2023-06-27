package leetcode.ubiquant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : zhaochengming
 * @date : 2023/6/27 14:14
 * @description : https://leetcode.cn/contest/ubiquant2022/problems/xdxykd/
 * 可以读通讯稿的组数
 */
public class Q001_NumOfPairs {
    static class Solution {
        public int numberOfPairs(int[] nums) {
            Map<Long, Integer> m = new HashMap<>();
            for (int num: nums) {
                long v = inverse(num) - num;
                m.put(v, m.getOrDefault(v, 0) + 1);
            }

            long mod = 1000000007L;
            int ans = 0;
            for (int n: m.values()) {
                ans += (((long) n * (n - 1)) % mod) / 2;
                ans %= mod;
            }
            return ans;

        }

        private long inverse(int num) {
            if (num < 10) return num;
            long ans = 0;
            while (num > 0) {
                ans = ans * 10 + num % 10;
                num /= 10;
            }
            return ans;
        }
    }
}
