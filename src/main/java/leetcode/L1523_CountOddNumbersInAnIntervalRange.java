package leetcode;


/**
 * @author : zhaochengming
 * @date : 2022/5/11 23:41
 * @description : https://leetcode.cn/problems/count-odd-numbers-in-an-interval-range/
 */
public class L1523_CountOddNumbersInAnIntervalRange {

    static class Solution {
        public int countOdds(int low, int high) {
            int span = high - low + 1;
            return span % 2 == 0 ? span / 2 : span / 2 + low % 2;
        }

        // [0...x] 之间的奇数
        private int pre(int x) {
            return (x + 1) / 2;
        }


        public int countOdds2(int low, int high) {
            return pre(high) - pre(low - 1);
        }
    }
}
