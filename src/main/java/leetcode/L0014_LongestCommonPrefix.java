package leetcode;

/**
 * @author : zhaochengming
 * @date : 2023/4/25 19:34
 * @description : https://leetcode.cn/problems/longest-common-prefix/
 * 最长公共前缀 - 纵向扫描
 */
public class L0014_LongestCommonPrefix {
    static class Solution {
        public String longestCommonPrefix(String[] arr) {
            if (arr.length == 1) {
                return arr[0];
            }
            String x = arr[0];
            for (int i = 0; i < x.length(); i ++) {
                char c = x.charAt(i);
                for (int j = 1; j < arr.length; j ++) {
                    if (i >= arr[j].length() || arr[j].charAt(i) != c) {
                        return x.substring(0, i);
                    }
                }
            }

            return x;

        }
    }
}
