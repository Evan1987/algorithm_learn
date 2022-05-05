package labuladong.chap05;

/**
 * @author : zhaochengming
 * @date : 2022/5/5 21:25
 * @description : O(1) 空间复杂度，获取不重复元素数量
 */
public class RemoveDuplicates {

    public static int solve(int[] nums) {
        int slow = 0, fast = 1;
        int n = nums.length;
        while (fast < n) {
            if (nums[fast] != nums[slow]) {
                slow ++;
                nums[slow] = nums[fast];
            }
            fast ++;
        }

        return slow + 1;
    }

}
