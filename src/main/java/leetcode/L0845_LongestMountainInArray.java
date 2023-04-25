package leetcode;

/**
 * @author : zhaochengming
 * @date : 2023/4/25 12:13
 * @description : https://leetcode.cn/problems/longest-mountain-in-array/
 * 数组中的最长山脉
 */
public class L0845_LongestMountainInArray {
    static class Solution {
        public int longestMountain(int[] arr) {
            int ans = 0;
            int n = arr.length;
            int left = 0;                   // 左侧山脚
            while (left < n - 2) {
                int right = left + 1;       // 右侧山脚
                if (arr[left] < arr[left + 1]) {
                    // right 到山顶
                    while (right < n - 1 && arr[right] < arr[right + 1]) {
                        right ++;
                    }

                    // 确认是否是山顶
                    if (right < n - 1 && arr[right] > arr[right + 1]) {
                        // right到山脚
                        while (right < n - 1 && arr[right] > arr[right + 1]) {
                            right ++;
                        }
                        ans = Math.max(ans, right - left + 1);
                    } else {
                        // right 不是山顶
                        right ++;
                    }
                }
                left = right;
            }

            return ans;
        }
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        int[] arr = {2,1,4,7,3,2,5};
        System.out.println(sol.longestMountain(arr));
    }
}
