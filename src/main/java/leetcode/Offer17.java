package leetcode;

/**
 * @author : zhaochengming
 * @date : 2023/5/23 20:13
 * @description : https://leetcode.cn/problems/da-yin-cong-1dao-zui-da-de-nwei-shu-lcof/
 * 打印出从1到最大的n位数
 */
public class Offer17 {
    static class Solution {
        public int[] printNumbers(int n) {
            int max = 1;
            int i = 0;
            while (i < n) {
                max *= 10;
                i ++;
            }

            int[] ans = new int[max - 1];
            i = 0;
            while (i < ans.length) {
                ans[i ++] = i;
            }
            return ans;
        }
    }
}
