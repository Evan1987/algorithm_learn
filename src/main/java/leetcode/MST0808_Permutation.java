package leetcode;

import java.util.*;

/**
 * @author : zhaochengming
 * @date : 2023/7/30 13:24
 * @description : https://leetcode.cn/problems/permutation-ii-lcci/
 * 有重复字符串的排列组合
 */
public class MST0808_Permutation {
    static class Solution {
        List<String> ans = new LinkedList<>();

        public String[] permutation(String S) {
            char[] arr = S.toCharArray();
            Arrays.sort(arr);

            backTrack(arr, 0);
            return ans.toArray(new String[0]);
        }

        // 对 arr[idx] 进行不同字符的替换
        private void backTrack(char[] arr, int idx) {
            if (idx == arr.length) {
                ans.add(new String(arr));
                return;
            }

            for (int i = idx; i < arr.length; i ++) {
                // 禁止使用重复字符替换，与arr[idx]一致或者前后两次替换一致
                if (i > idx && (arr[i] == arr[idx] || arr[i] == arr[i - 1])) continue;
                swap(arr, i, idx);
                backTrack(arr, idx + 1);
                swap(arr, i, idx);  // 还原回来
            }
        }

        private void swap(char[] arr, int i, int j) {
            char temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }
}
