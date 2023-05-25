package leetcode;

/**
 * @author : zhaochengming
 * @date : 2023/5/25 14:49
 * @description : https://leetcode.cn/problems/number-of-steps-to-reduce-a-number-in-binary-representation-to-one/
 * 将二进制表示减少到1的步骤数
 */
public class L1404_NumOfStepsToReduceToOne {
    static class Solution {
        public int numSteps(String s) {
            int n = s.length();
            int ans = 0;
            boolean meet1 = false;
            for (int i = n - 1; i >= 0; i --) {
                if (s.charAt(i) == '0') {
                    ans += meet1 ? 2 : 1;
                } else {
                    if (!meet1) {
                        if (i != 0) ans += 2;
                        meet1 = true;
                    } else {
                        ans ++;
                    }
                }
            }
            return ans;
        }
    }
}
