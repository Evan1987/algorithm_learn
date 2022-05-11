package leetcode;

import java.util.*;

/**
 * @author : zhaochengming
 * @date : 2022/5/12 1:09
 * @description : https://leetcode.cn/problems/majority-element/
 */
public class L0169_MajorityElement {

    static class Solution {

        // Boyer-Moore
        public int majorityElement(int[] nums) {
            int n = nums.length;
            if (n == 1) {
                return nums[0];
            }

            int candidate = nums[0];
            int count = 1;
            for (int i = 1; i < n; i ++) {
                if (nums[i] == candidate) {
                    count ++;
                } else {
                    count --;
                    if (count < 0) {
                        candidate = nums[i];
                        count = 1;
                    }
                }
            }

            return candidate;
        }
    }
}
