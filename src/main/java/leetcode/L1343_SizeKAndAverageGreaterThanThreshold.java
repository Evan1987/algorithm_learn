package leetcode;

/**
 * @author : zhaochengming
 * @date : 2023/5/15 19:24
 * @description : https://leetcode.cn/problems/number-of-sub-arrays-of-size-k-and-average-greater-than-or-equal-to-threshold/
 * 大小为 K 且平均值大于等于阈值的子数组数目
 */
public class L1343_SizeKAndAverageGreaterThanThreshold {
    static class Solution {
        public int numOfSubarrays(int[] arr, int k, int threshold) {
            int ans = 0;
            int sum = 0;
            for (int i = 0; i < k; i ++) {
                sum += arr[i];
            }

            if (sum / k >= threshold) {
                ans ++;
            }

            for (int i = k; i < arr.length; i ++) {
                sum -= arr[i - k];
                sum += arr[i];
                if (sum / k >= threshold) {
                    ans ++;
                }
            }

            return ans;

        }
    }
}
