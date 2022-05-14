package leetcode;

import java.util.*;

/**
 * @author : zhaochengming
 * @date : 2022/5/15 1:03
 * @description : https://leetcode.cn/problems/reverse-words-in-a-string/
 */
public class L0151_ReverseWordsInAString {
    static class Solution {
        // 禁止使用JAVA String API
        public String reverseWords(String s) {
            char[] arr = transform(s);
            reverse(arr, 0, arr.length - 1);

            // 找到单词，反转单词
            int start = 0;
            int end = 0;
            while (end < arr.length) {
                while (end < arr.length && arr[end] != ' ')
                    end ++;
                reverse(arr, start, end - 1);
                start = end + 1;
                end = start;
            }

            reverse(arr, start, end);

            return new String(arr);

        }

        // 转换为可变数组，且清除多余空格
        private char[] transform(String s) {
            int n = s.length();
            char[] temp = new char[n];
            int curr = 0;

            for (int i = 0; i < n; i ++) {
                char c = s.charAt(i);
                if (c == ' ') {
                    // 开头|结尾|连续多个
                    if (curr == 0 || i == n - 1 || (i < n - 1 && s.charAt(i + 1) == ' ')) continue;
                    //
                }
                temp[curr ++] = c;
            }

            char[] arr = new char[curr];
            System.arraycopy(temp, 0, arr, 0, curr);
            return arr;
        }

        // 反转数组
        private void reverse(char[] arr, int start, int end) {
            int i = start;
            int j = end;
            while (i < j) {
                char t = arr[i];
                arr[i] = arr[j];
                arr[j] = t;
                i ++;
                j --;
            }
        }
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        System.out.println(sol.reverseWords("the sky is blue"));
    }
}
