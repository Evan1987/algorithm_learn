package leetcode;


/**
 * @author : zhaochengming
 * @date : 2022/5/17 14:44
 * @description : https://leetcode.cn/problems/longest-subarray-of-1s-after-deleting-one-element/
 */
public class L1493_LongestSubArrayOf1sAfterDeleting1Element {
    static class Solution {

        // 模拟，时间O(n)，空间O(1)
        public int longestSubarray(int[] nums) {
            int n = nums.length;
            int ans = 0;

            // 缩小边界
            int left = 0;
            while (left < n && nums[left] == 0) left ++;
            if (left == n) return ans;

            int right = n - 1;
            while (right > left && nums[right] == 0) right --;
            if (left == right) return nums[left];

            int last = 0;
            for (int i = left; i <= right; ) {
                if (nums[i] == 0) {
                    last = 0;
                    i ++;
                    continue;
                }

                int j = i;
                while (j <= right && nums[j] == 1) j ++;  // 直到 nums[j] == 0
                ans = Math.max(ans, last + j - i);
                last = j - i;
                i = j + 1;
            }

            return ans == n ? n - 1 : ans;
        }

        // dp  时间O(n), 空间O(n)
        public int longestSubarray2(int[] nums) {
            int n = nums.length;

            // p0[i] 以 nums[i] 结尾的最长 1子串长度
            int[] p0 = new int[n];
            p0[0] = nums[0] == 0 ? 0 : 1;

            // if nums[i] == 1, p0[i] = p0[i - 1] + 1
            // if nums[i] == 0, p0[i] = 0
            for (int i = 1; i < n; i ++) {
                p0[i] = nums[i] == 0 ? 0 : p0[i - 1] + 1;
            }

            // p1[i] 以 nums[i] 结尾的 "可以删除1个元素的" 最长1 子串长度
            int[] p1 = new int[n];
            p1[0] = nums[0] == 0 ? 0 : 1;
            int ans = p1[0];

            // if nums[i] == 1, p1[i] = p1[i - 1] + 1
            // if nums[i] == 0, p1[i] = p0[i - 1]
            for (int i = 1; i < n; i ++) {
                p1[i] = nums[i] == 0 ? p0[i - 1] : p1[i - 1] + 1;
                ans = Math.max(ans, p1[i]);
            }

            return ans == n ? n - 1 : ans;
        }

        // dp空间优化 时间O(n), 空间O(1)
        public int longestSubarray3(int[] nums) {
            int n = nums.length;

            int p0 = nums[0] == 0 ? 0 : 1;
            int p1 = p0;
            int ans = p0;

            // if nums[i] == 1, p0[i] = p0[i - 1] + 1, p1[i] = p1[i - 1] + 1
            // if nums[i] == 0, p0[i] = 0, p1[i] = p0[i - 1]
            for (int i = 1; i < n; i ++) {
                if (nums[i] == 0) {
                    p1 = p0;
                    p0 = 0;
                } else {
                    p0 ++;
                    p1 ++;
                }
                ans = Math.max(ans, p1);
            }

            return ans == n ? n - 1 : ans;
        }
    }
}
