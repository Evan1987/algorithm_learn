package leetcode;

/**
 * @author : zhaochengming
 * @date : 2023/5/20 20:33
 * @description : https://leetcode.cn/problems/que-shi-de-shu-zi-lcof/
 * 缺失的数字
 */
public class Offer2_053 {
    static class Solution {
        public int missingNumber(int[] nums) {
            int expect = 0;
            for (int x: nums) {
                if (x != expect)
                    return expect;
                expect ++;
            }
            return expect;
        }
    }
}
