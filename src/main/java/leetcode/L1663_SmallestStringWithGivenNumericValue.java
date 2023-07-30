package leetcode;

/**
 * @author : zhaochengming
 * @date : 2023/7/30 11:19
 * @description : https://leetcode.cn/problems/smallest-string-with-a-given-numeric-value/
 * 给定数值的最小字符串，长度为n且数值等于k的最小字符串
 */
public class L1663_SmallestStringWithGivenNumericValue {
    static class Solution {

        // 前期尽量选取小的字母，直到平均值 k / n > 26
        // (k - x) / (n - 1) <= 26   ---->  x >= k + 26 - 26 * n
        public String getSmallestString(int n, int k) {
            StringBuilder sb = new StringBuilder();
            while (n > 0) {
                int x = Math.max(k + 26 - 26 * n, 1);
                sb.append((char) ('a' + x - 1));
                n --;
                k -= x;
            }

            return sb.toString();
        }
    }
}
