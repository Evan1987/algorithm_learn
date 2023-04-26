package leetcode;


/**
 * @author : zhaochengming
 * @date : 2023/4/26 09:36
 * @description : https://leetcode.cn/problems/sub-sort-lcci/
 * 部分排序
 */
public class MST1616_SubSort {
    static class Solution {
        public int[] subSort(int[] array) {
            int n = array.length;
            int left = -1;
            int right = -1;
            int min = Integer.MAX_VALUE;
            int max = Integer.MIN_VALUE;
            for (int i = 0; i < n; i ++) {
                if (array[i] < max) right = i;
                else max = array[i];
            }

            for (int i = n - 1; i >= 0; i --) {
                if (array[i] > min) left = i;
                else min = array[i];
            }

            return new int[]{left, right};
        }
    }
}
