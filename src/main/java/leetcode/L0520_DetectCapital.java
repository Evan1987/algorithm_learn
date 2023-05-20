package leetcode;

/**
 * @author : zhaochengming
 * @date : 2023/5/20 18:29
 * @description : https://leetcode.cn/problems/detect-capital/
 * 检测大写字母
 */
public class L0520_DetectCapital {
    static class Solution {
        public boolean detectCapitalUse(String word) {
            int n = word.length();
            if (n == 1) return true;

            if (word.charAt(0) > 90 && word.charAt(1) <= 90) {
                return false;
            }

            // 无论第一个字符是什么，剩余字符的大小写必须与第二个字符相同
            boolean flag = word.charAt(1) <= 90;
            for (int i = 2; i < n; i ++) {
                if (word.charAt(i) <= 90 ^ flag) {
                    return false;
                }
            }
            return true;
        }
    }
}
