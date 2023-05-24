package leetcode;

/**
 * @author : zhaochengming
 * @date : 2023/5/24 14:00
 * @description : https://leetcode.cn/problems/element-appearing-more-than-25-in-sorted-array/
 * 有序数组中出现次数超过25%的元素
 */
public class L1287_ElementAppearingMoreThan25InSortedArray {
    static class Solution {
        public int findSpecialInteger(int[] arr) {
            int n = arr.length;
            int span = n / 4 + 1;

            for (int i = 0; i < n; i += span) {
                int lower = findLowerBound(arr, arr[i]);
                int upper = findUpperBound(arr, arr[i]);
                if ((upper - lower + 1) * 4 > n) {
                    return arr[i];
                }
            }
            return -1;
        }

        private int findLowerBound(int[] arr, int target) {
            int n = arr.length;
            int left = 0, right = n - 1;
            while (left <= right) {
                int mid = left + (right - left) / 2;
                if (arr[mid] < target) {
                    left = mid + 1;
                } else if (arr[mid] > target) {
                    right = mid - 1;
                } else {
                    right = mid - 1;
                }
            }

            if (left >= n || arr[left] != target) {
                return -1;
            }
            return left;
        }

        private int findUpperBound(int[] arr, int target) {
            int n = arr.length;
            int left = 0, right = n - 1;
            while (left <= right) {
                int mid = left + (right - left) / 2;
                if (arr[mid] < target) {
                    left = mid + 1;
                } else if (arr[mid] > target) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }

            if (right < 0 || arr[right] != target) {
                return -1;
            }
            return right;
        }
    }

}
