package leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : zhaochengming
 * @date : 2023/4/25 19:46
 * @description : https://leetcode.cn/problems/A1NYOS/
 * 0 和 1 个数相同的子数组  前缀和，将 0 看作 -1
 */
public class Offer2_011 {
    static class Solution {
        public int findMaxLength(int[] nums) {
            int ans = 0;
            Map<Integer, Integer> m = new HashMap<>();
            int count = 0;
            m.put(0, -1);
            for (int i = 0; i < nums.length; i ++) {
                if (nums[i] == 1) {
                    count ++;
                } else {
                    count --;
                }

                if (m.containsKey(count)) {
                    ans = Math.max(ans, i - m.get(count));
                } else {
                    m.put(count, i);
                }

            }

            return ans;
        }
    }
}
