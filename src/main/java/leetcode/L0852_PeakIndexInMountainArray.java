package leetcode;


/**
 * @author : zhaochengming
 * @date : 2022/5/16 23:54
 * @description : https://leetcode.cn/problems/peak-index-in-a-mountain-array/
 */
public class L0852_PeakIndexInMountainArray {
    static class Solution {
        public int peakIndexInMountainArray(int[] arr) {

            int left = 0;
            int right = arr.length - 1;
            while (left < right) {
                int mid = left + (right - left) / 2;
                if (arr[mid] < arr[mid + 1]) {
                    left = mid + 1;
                } else {
                    right = mid;
                }
            }

            return left;

        }
    }
}
