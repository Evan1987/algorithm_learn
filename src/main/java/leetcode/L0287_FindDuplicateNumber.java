package leetcode;


/**
 * @author : zhaochengming
 * @date : 2022/5/17 21:13
 * @description : https://leetcode.cn/problems/find-the-duplicate-number/
 */
public class L0287_FindDuplicateNumber {
    static class Solution {

        // 不能修改nums，所以正负号法不可以用，快慢指针
        public int findDuplicate(int[] nums) {
//            for (int i = 0; i < nums.length; i ++) {
//                if (nums[nums[i]] < 0) {
//                    return nums[i];
//                }
//
//                nums[nums[i]] *= -1;
//            }
//
//            return -1;

            int slow, fast;
            slow = fast = 0;

            do {
                slow = nums[slow];
                fast = nums[nums[fast]];
            } while (slow != fast);

            slow = 0;
            while (slow != fast) {
                slow = nums[slow];
                fast = nums[fast];
            }

            return slow;
        }
    }
}
