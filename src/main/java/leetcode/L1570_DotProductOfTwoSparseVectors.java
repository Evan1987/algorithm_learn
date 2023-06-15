package leetcode;


import java.util.*;

/**
 * @author : zhaochengming
 * @date : 2023/6/15 19:44
 * @description : https://leetcode.cn/problems/dot-product-of-two-sparse-vectors/
 * 稀疏向量的点积
 */
public class L1570_DotProductOfTwoSparseVectors {
    static class SparseVector {

        private final Map<Integer, Integer> m;

        SparseVector(int[] nums) {
            this.m = new HashMap<>();
            for (int i = 0; i < nums.length; i ++) {
                if (nums[i] == 0) continue;
                this.m.put(i, nums[i]);
            }
        }

        // Return the dotProduct of two sparse vectors
        public int dotProduct(SparseVector vec) {
            SparseVector a, b;
            if (this.m.size() < vec.m.size()) {
                a = this;
                b = vec;
            } else {
                a = vec;
                b = this;
            }

            int ans = 0;
            for (Map.Entry<Integer, Integer> entry: a.m.entrySet()) {
                if (b.m.containsKey(entry.getKey())) {
                    ans += b.m.get(entry.getKey()) * entry.getValue();
                }
            }

            return ans;
        }
    }
}
