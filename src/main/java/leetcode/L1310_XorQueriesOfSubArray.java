package leetcode;


/**
 * @author : zhaochengming
 * @date : 2022/5/19 10:54
 * @description : https://leetcode.cn/problems/xor-queries-of-a-subarray/
 */
public class L1310_XorQueriesOfSubArray {
    static class Solution {
        public int[] xorQueries(int[] arr, int[][] queries) {
            int n = arr.length;

            // arr[left] ^... arr[right]
            // = (arr[0] ^... arr[left - 1]) ^ (arr[0] ^... arr[left - 1]) ^ (arr[left] ^... arr[right])
            // = pre(left - 1) ^ pre(right)
            int[] xors = new int[n];
            int curr = 0;
            for (int i = 0; i < n; i ++) {
                xors[i] = curr ^ arr[i];
                curr = xors[i];
            }

            int[] ans = new int[queries.length];
            for (int i = 0; i < queries.length; i ++) {
                int left = queries[i][0], right = queries[i][1];
                if (left == 0) {
                    ans[i] = xors[right];
                } else {
                    ans[i] = xors[left - 1] ^ xors[right];
                }
            }

            return ans;

        }
    }
}
