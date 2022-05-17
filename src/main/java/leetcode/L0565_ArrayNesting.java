package leetcode;


/**
 * @author : zhaochengming
 * @date : 2022/5/16 23:45
 * @description : https://leetcode.cn/problems/array-nesting/
 */
public class L0565_ArrayNesting {

    static class Solution {
        public int arrayNesting(int[] nums) {
            int ans = 0;
            int n = nums.length;
            for (int i = 0; i < n; i ++) {
                if (nums[i] == -1) continue;
                int curr = i, count = 0;
                while (nums[curr] != -1) {
                    count ++;
                    int temp = nums[curr];
                    nums[curr] = -1;
                    curr = temp;
                }
                if (count > n / 2) return count;
                ans = Math.max(ans, count);
            }
            return ans;
        }
    }
}
