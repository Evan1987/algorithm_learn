package leetcode;

/**
 * @author : zhaochengming
 * @date : 2023/4/28 21:14
 * @description : https://leetcode.cn/problems/one-away-lcci/
 * 一次编辑
 */
public class MST0105_OneEdit {
    static class Solution {
        public boolean oneEditAway(String first, String second) {
            int m = first.length();
            int n = second.length();

            if (m - n == 1) {
                return oneCharInsert(first, second);
            }

            if (n - m == 1) {
                return oneCharInsert(second, first);
            }

            if (m == n) {
                return oneCharDiff(first, second);
            }

            return false;
        }

        // 是否只差增加一个字符 shorter增加一个字符即可和longer相同
        private boolean oneCharInsert(String longer, String shorter) {
            int i = 0, j = 0;

            while (i < longer.length() && j < shorter.length()) {
                if (longer.charAt(i) == shorter.charAt(j)) {
                    j ++;
                }
                i ++;

                if (i - j > 1) {
                    return false;
                }
            }

            return true;

        }

        // 长度相同，是否只有一个字符不同
        private boolean oneCharDiff(String a, String b) {
            boolean foundDiff = false;
            for (int i = 0; i < a.length(); i ++) {
                if (a.charAt(i) != b.charAt(i)) {
                    if (!foundDiff) {
                        foundDiff = true;
                    } else {
                        return false;
                    }
                }
            }

            return true;
        }
    }
}
