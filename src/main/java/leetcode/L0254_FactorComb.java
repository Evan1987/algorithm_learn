package leetcode;

import java.util.*;

/**
 * @author : zhaochengming
 * @date : 2023/5/19 11:02
 * @description : https://leetcode.cn/problems/factor-combinations/
 * 因子组合
 */
public class L0254_FactorComb {
    static class Solution {

        Map<Integer, List<List<Integer>>> cache = new HashMap<>();

        public List<List<Integer>> getFactors(int n) {
            return dfs(n, 2);
        }

        private List<List<Integer>> dfs(int x, int start) {
            List<List<Integer>> ans = new LinkedList<>();

            if (cache.containsKey(x)) {
                for (List<Integer> nums: cache.get(x)) {
                    if (nums.get(0) >= start) {
                        ans.add(nums);
                    }
                }
                return ans;
            }

            for (int i = start; i < (int) Math.sqrt(x) + 1; i ++) {
                if (x % i == 0) {
                    List<Integer> factors = new LinkedList<>();
                    factors.add(i);
                    factors.add(x / i);
                    ans.add(factors);

                    for (List<Integer> nums: dfs(x / i, i)) {
                        List<Integer> other = new LinkedList<>();
                        other.add(i);
                        other.addAll(nums);
                        ans.add(other);
                    }
                }
            }

            cache.put(x, ans);
            return ans;
        }
    }
}
