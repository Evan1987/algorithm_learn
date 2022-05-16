package leetcode;

import java.util.*;

/**
 * @author : zhaochengming
 * @date : 2022/5/8 22:24
 * @description :https://leetcode-cn.com/problems/find-all-duplicates-in-an-array/
 */
public class L0442_FindAllDuplicatesInAnArray {
    static class Solution {
        public List<Integer> findDuplicates(int[] nums) {
            List<Integer> res = new LinkedList<>();

            for (int i = 0; i < nums.length; i ++) {
                int num = Math.abs(nums[i]);
                if (nums[num - 1] < 0) {
                    res.add(num);
                } else {
                    nums[num - 1] *= -1;
                }
            }
            return res;
        }
    }
}
