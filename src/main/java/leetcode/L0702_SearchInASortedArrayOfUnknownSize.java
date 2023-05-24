package leetcode;

import leetcode.utils.ArrayReader;

/**
 * @author : zhaochengming
 * @date : 2023/5/24 15:22
 * @description : https://leetcode.cn/problems/search-in-a-sorted-array-of-unknown-size/
 * 探索长度未知的有序数组
 */
public class L0702_SearchInASortedArrayOfUnknownSize {
    static class Solution {
        public int search(ArrayReader reader, int target) {
            int left = 0, right = 10000;
            while (left <= right) {
                int mid = left + (right - left) / 2;
                int curr = reader.get(mid);
                if (curr < target) {
                    left = mid + 1;
                } else if (curr > target) {
                    right = mid - 1;
                } else {
                    return mid;
                }
            }

            return -1;
        }
    }
}
