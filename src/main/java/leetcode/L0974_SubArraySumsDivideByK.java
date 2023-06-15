package leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : zhaochengming
 * @date : 2023/6/15 19:01
 * @description : https://leetcode.cn/problems/subarray-sums-divisible-by-k/
 * 和可被k整除的子数组
 */
public class L0974_SubArraySumsDivideByK {
    static class Solution {
        public int subarraysDivByK(int[] nums, int k) {
            Map<Integer, Integer> record = new HashMap<>();
            record.put(0, 1);  // 考虑前缀和本身可被整除的情况

            int sum = 0, ans = 0;
            for (int num: nums) {
                sum += num;
                int mod = (sum % k + k) % k;                            // 使余数不为负
                record.put(mod, record.getOrDefault(mod, 0) + 1);
            }

            for (Map.Entry<Integer, Integer> entry: record.entrySet()) {
                if (entry.getValue() > 1) {
                    ans += entry.getValue() * (entry.getValue() - 1) / 2;   // 任意组合都可以保证组合
                }
            }

            return ans;
        }
    }
}
