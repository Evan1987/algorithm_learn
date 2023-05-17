package leetcode;

/**
 * @author : zhaochengming
 * @date : 2023/5/17 15:44
 * @description : https://leetcode.cn/problems/three-consecutive-odds/
 * 是否存在连续3个奇数
 */
public class L1550_ThreeConsecutiveOdds {
    static class Solution {
        public boolean threeConsecutiveOdds(int[] arr) {
            if (arr.length < 3) return false;
            int count = 0;
            for (int x: arr) {
                if (x % 2 == 0) {
                    count = 0;
                } else {
                    count ++;
                    if (count == 3) return true;
                }
            }
            return false;
        }
    }
}
