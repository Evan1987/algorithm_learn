package leetcode;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/*
 * 两个数组交集
 * @author : zhaochengming.zcm
 * @date : 2025/12/10 00:00
 * https://leetcode.cn/problems/intersection-of-two-arrays
 */
public class L0349_IntersectionOfTwoArrays {
    static class Solution {
        public int[] intersection(int[] nums1, int[] nums2) {
            Arrays.sort(nums1);
            Arrays.sort(nums2);
            int n1 = nums1.length, n2 = nums2.length;
            int[] res = new int[Math.min(n1, n2)];
            int index1 = 0, index2 = 0, index = 0;
            int pre = -1;
            while (index1 < n1 && index2 < n2) {
                int v1 = nums1[index1], v2 = nums2[index2];
                if (v1 == v2) {
                    // 保证数据唯一性
                    if (v1 != pre) {
                        res[index ++] = v1;
                        pre = v1;
                    }
                    index1 ++;
                    index2 ++;
                } else if (v1 < v2) {
                    index1 ++;
                } else {
                    index2 ++;
                }
            }
            return Arrays.copyOfRange(res, 0, index);
        }
    }
}
