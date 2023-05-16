package leetcode;

/**
 * @author : zhaochengming
 * @date : 2023/5/16 17:36
 * @description : https://leetcode.cn/problems/smallest-even-multiple/
 * 最小偶倍数
 */
public class L2413_SmallestEvenMultiple {
    static class Solution {
        public int smallestEvenMultiple(int n) {
            return n % 2 == 1 ? n * 2 : n;
        }
    }
}
