package leetcode;


/**
 * @author : zhaochengming
 * @date : 2022/5/15 15:19
 * @description : https://leetcode.cn/problems/check-if-all-as-appears-before-all-bs/
 */
public class L2124_CheckIfAllAsAppearsBeforeAllBs {
    static class Solution {
        public boolean checkString(String s) {
            char curr = s.charAt(0);
            for (char c: s.toCharArray()) {
                if (c < curr) return false;
                curr = c;
            }

            return true;
        }
    }
}
