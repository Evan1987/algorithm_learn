package leetcode;

import java.util.*;

/**
 * @author : zhaochengming
 * @date : 2022/5/23 14:48
 * @description : https://leetcode.cn/problems/relative-sort-array/
 */
public class L1122_RelativeSortArray {

    static class Solution {
        public int[] relativeSortArray(int[] arr1, int[] arr2) {
            if (arr1.length == 0) return arr1;

            int n = arr2.length;
            Map<Integer, Integer> priority = new HashMap<>();
            for (int i = 0; i < n; i ++) {
                priority.put(arr2[i], i);
            }

            return Arrays.stream(arr1).boxed()
                    .sorted(Comparator.comparingInt(x -> priority.getOrDefault(x, n + x)))
                    .mapToInt(x -> x)
                    .toArray();
        }
    }

    // 计数排序
    public int[] relativeSortArray2(int[] arr1, int[] arr2) {
        int upper = 0;
        int[] freq = new int[1001];
        for (int x: arr1) {
            upper = Math.max(upper, x);
            freq[x] ++;
        }

        int[] ans = new int[arr1.length];

        int index = 0;
        // 存在 arr2中的
        for (int x: arr2) {
            for (int i = 0; i < freq[x]; i ++) {
                ans[index ++] = x;
            }

            freq[x] = 0;
        }

        // 不存在 arr2中的
        for (int x = 0; x <= upper; x ++) {
            for (int j = 0; j < freq[x]; j ++) {
                ans[index ++] = x;
            }
        }

        return ans;
    }

}
