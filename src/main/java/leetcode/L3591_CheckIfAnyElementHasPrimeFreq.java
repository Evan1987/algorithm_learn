package leetcode;

/**
 * @author : zhaochengming
 * @date : 2025/12/13 12:33
 * @description : https://leetcode.cn/problems/check-if-any-element-has-prime-frequency/
 */
public class L3591_CheckIfAnyElementHasPrimeFreq {
    static class Solution {
        private final static int MAX = 101;
        private final static boolean[] NOT_PRIME = new boolean[MAX];
        static {
            NOT_PRIME[0] = NOT_PRIME[1] = true;
            for (int i = 2; i * i < MAX; i ++) {
                if (NOT_PRIME[i]) continue;
                // i的各倍数
                for (int j = i * i; j < MAX; j += i) {
                    NOT_PRIME[j] = true;
                }
            }
        }


        public boolean checkPrimeFrequency(int[] nums) {
            int[] count = new int[MAX];
            for (int value: nums) {
                count[value] ++;
            }

            for (int cnt: count) {
                if (cnt > 0 && !NOT_PRIME[cnt]) return true;
            }
            return false;
        }
    }
}
