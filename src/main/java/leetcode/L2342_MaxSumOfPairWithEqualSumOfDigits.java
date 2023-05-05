package leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : zhaochengming
 * @date : 2023/5/5 18:54
 * @description : https://leetcode.cn/problems/max-sum-of-a-pair-with-equal-sum-of-digits/
 */
public class L2342_MaxSumOfPairWithEqualSumOfDigits {
    static class Solution {
        public int maximumSum(int[] nums) {
            int ans = -1;
            Map<Integer, Integer> m = new HashMap<>();
            for (int num: nums) {
                int s = digitSum(num);
                if (m.containsKey(s)) {
                    int another = m.get(s);
                    if (num > another) {
                        m.put(s, num);
                    }
                    ans = Math.max(ans, another + num);
                } else {
                    m.put(s, num);
                }


            }
            return ans;

        }

        private static int digitSum(int num) {
            int sum = 0;
            while (num > 0) {
                sum += num % 10;
                num /= 10;
            }
            return sum;
        }
    }
}
