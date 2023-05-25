package leetcode;

import java.util.Arrays;

/**
 * @author : zhaochengming
 * @date : 2023/5/25 10:26
 * @description : https://leetcode.cn/problems/odd-string-difference/
 * 差值数组不同的字符串
 */
public class L2451_OddStringDifference {
    static class Solution {
        public String oddString(String[] words) {

            int[] a = getDiffArray(words[0]);
            int[] b = getDiffArray(words[1]);
            if (Arrays.equals(a, b)) {
                for (int i = 2; i < words.length; i ++) {
                    if (!Arrays.equals(getDiffArray(words[i]), a)) {
                        return words[i];
                    }
                }
            }

            return Arrays.equals(getDiffArray(words[2]), a) ? words[1] : words[0];
        }

        private static int[] getDiffArray(String word) {
            int n = word.length();
            int[] res = new int[n - 1];
            for (int i = 0; i < n - 1; i ++) {
                res[i] = word.charAt(i + 1) - word.charAt(i);
            }
            return res;
        }

    }

    public static void main(String[] args) {
        String[] words = {"abc", "wzy", "adc"};
        Solution sol = new Solution();
        System.out.println(sol.oddString(words));
    }
}
