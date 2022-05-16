package leetcode;

import java.util.*;

/**
 * @author : zhaochengming
 * @date : 2022/5/16 16:43
 * @description : https://leetcode.cn/problems/avoid-flood-in-the-city/
 */
public class L1488_AvoidFloodInTheCity {
    static class Solution {
        public int[] avoidFlood(int[] rains) {

            int n = rains.length;
            TreeSet<Integer> sunnyDays = new TreeSet<>();
            Map<Integer, Integer> poolRainy = new HashMap<>();

            int[] ans = new int[n];
            Arrays.fill(ans, 1);

            for (int i = 0; i < n; i ++) {
                int pool = rains[i];
                if (pool == 0) {
                    sunnyDays.add(i);
                } else {
                    if (poolRainy.containsKey(pool)) {
                        Integer index = sunnyDays.higher(poolRainy.get(pool));
                        if (index == null) return new int[0];
                        ans[index] = pool;
                        sunnyDays.remove(index);
                    }
                    poolRainy.put(pool, i);
                    ans[i] = -1;
                }
            }
            return ans;
        }
    }
}
