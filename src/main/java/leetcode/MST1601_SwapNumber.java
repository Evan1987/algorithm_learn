package leetcode;

/**
 * @author : zhaochengming
 * @date : 2023/5/23 19:54
 * @description : https://leetcode.cn/problems/swap-numbers-lcci/
 * 不使用中间变量，交换数字
 * 性质 a ^ a = 0, 0 ^ b = b, a ^ b = b ^ a, a ^ b ^ a = b
 */
public class MST1601_SwapNumber {
    static class Solution {
        public int[] swapNumbers(int[] numbers) {
            numbers[0] = numbers[0] ^ numbers[1];
            numbers[1] = numbers[0] ^ numbers[1];
            numbers[0] = numbers[0] ^ numbers[1];
            return numbers;
        }
    }
}
