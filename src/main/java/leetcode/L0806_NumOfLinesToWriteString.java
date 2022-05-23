package leetcode;


/**
 * @author : zhaochengming
 * @date : 2022/5/22 15:30
 * @description : https://leetcode.cn/problems/number-of-lines-to-write-string/
 */
public class L0806_NumOfLinesToWriteString {

    static class Solution {

        private static final int COUNT = 100;

        public int[] numberOfLines(int[] widths, String s) {
            int[] ans = new int[2];
            ans[0] = 1;
            int rest = COUNT;
            for (char c: s.toCharArray()) {
                if (widths[c - 'a'] > rest) {
                    ans[0] ++;
                    rest = COUNT;
                }

                rest -= widths[c - 'a'];
            }

            ans[1] = COUNT - rest;
            return ans;

        }
    }
}
