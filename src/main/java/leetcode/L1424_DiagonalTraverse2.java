package leetcode;

import java.util.*;

/**
 * @author : zhaochengming
 * @date : 2022/5/16 0:34
 * @description : https://leetcode.cn/problems/diagonal-traverse-ii/
 */
public class L1424_DiagonalTraverse2 {
    static class Solution {
        public int[] findDiagonalOrder(List<List<Integer>> nums) {
            int n = 0;
            Map<Integer, List<Integer>> map = new LinkedHashMap<>();

            int i = 0;
            for (List<Integer> sub: nums) {
                n += sub.size();
                int j = 0;
                for (int x: sub) {
                    map.putIfAbsent(i + j, new ArrayList<>());
                    map.get(i + j).add(x);
                    j ++;
                }
                i ++;
            }

            int[] res = new int[n];
            int curr = 0;
            for (Map.Entry<Integer, List<Integer>> e: map.entrySet()) {
                List<Integer> q = e.getValue();
                for (int k = q.size() - 1; k >= 0; k --) {
                    res[curr ++] = q.get(k);
                }
            }

            return res;

        }
    }

    public static void main(String[] args) {
        List<List<Integer>> nums = new ArrayList<>();
        nums.add(Arrays.asList(1, 2, 3));
        nums.add(Arrays.asList(4, 5, 6));
        nums.add(Arrays.asList(7, 8, 9));
        Solution sol = new Solution();
        for (int x: sol.findDiagonalOrder(nums)) {
            System.out.println(x);
        }
    }
}
