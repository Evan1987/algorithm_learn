package leetcode;

/**
 * @author : zhaochengming
 * @date : 2023/8/7 17:22
 * @description : https://leetcode.cn/problems/reverse-string/
 * 反转字符串，原位
 */
public class L0344_ReverseString {
    static class Solution {
        public void reverseString(char[] s) {
            int i = 0, j = s.length - 1;

            while (i < j) {
                char c = s[i];
                s[i] = s[j];
                s[j] = c;
                i ++;
                j --;
            }
        }
    }
}
