package leetcode;

import java.util.Arrays;

/**
 * @author : zhaochengming
 * @date : 2023/5/1 22:11
 * @description : https://leetcode.cn/problems/sum-of-mutated-array-closest-to-target/
 * 将数组中所有大于value的值，转变为value，其和最接近target
 */
public class L1300_SumOfMutatedArrayClosestToTarget {
    static class Solution {
        public int findBestValue(int[] arr, int target) {
            Arrays.sort(arr);
            int n = arr.length;
            int[] prefix = new int[n + 1]; // prefix[i] = sum(arr[:i])
            prefix[0] = 0;
            for (int i = 0; i < n; i ++) {
                prefix[i + 1] = prefix[i] + arr[i];
            }

            int ans = -1;
            int lo = 0, hi = arr[n - 1];
            while (lo <= hi) {
                int mid = lo + (hi - lo) / 2;
                int index = findRightBound(arr, mid);
                int sum = prefix[index] + (n - index) * mid;
                if (sum <= target) {
                    ans = mid;
                    lo = mid + 1;
                } else {
                    hi = mid - 1;
                }

            }

            int chooseSmall = Math.abs(checkSum(arr, ans) - target);
            int chooseBig = Math.abs(checkSum(arr, ans + 1) - target);

            return chooseSmall <= chooseBig ? ans : ans + 1;
        }

        private int checkSum(int[] arr, int value) {
            int sum = 0;
            for (int x: arr) {
                sum += Math.min(x, value);
            }
            return sum;
        }

        // 找到arr中大于value的最小值所在索引
        private int findRightBound(int[] arr, int value) {
            int n = arr.length;
            int lo = 0, hi = n - 1;
            while (lo <= hi) {
                int mid = lo + (hi - lo) / 2;
                if (arr[mid] < value) {
                    lo = mid + 1;
                } else if (arr[mid] > value) {
                    hi = mid - 1;
                } else {
                    lo = mid + 1;
                }
            }
            return lo;
        }
    }
}
