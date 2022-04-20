package labuladong.chap01;

/**
 * @author : zhaochengming
 * @date : 2022/4/19 23:00
 * @description : 在有序数组中寻找两数之和等于target
 */
public class TwoSumInSortedArray {

    public static int[] solve(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while (left < right) {
            int sum = nums[left] + nums[right];
            if (sum < target) {
                left ++;
            } else if (sum > target) {
                right --;
            } else {
                return new int[]{left, right};
            }
        }
        return new int[]{-1, -1};
    }

}
