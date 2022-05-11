package leetcode;

import utils.annotations.WatchTime;
import java.util.*;

/**
 * @author : zhaochengming
 * @date : 2022/5/12 0:00
 * @description : 采购方案：https://leetcode.cn/problems/4xy4Wx/
 */
public class LCP28 {

    static class Solution {

        private static final long MOD = 1000000007;

        private static long addCount(long count, long add) {
            count += add;
            return count % MOD;
        }

        @WatchTime(methodDesc = "快捷")
        public int purchasePlans(int[] nums, int target) {
            Arrays.sort(nums);
            int n = nums.length;
            long count = 0;
            if (nums[1] + nums[0] > target) {
                return 0;
            }

            if (nums[n - 2] + nums[n - 1] <= target) {
                count = (long) n * (n - 1) / 2;
                return  (int)(count % MOD);
            }

            int i = 0;
            while (nums[i] + nums[i + 1] <= target) {
                i ++;
            }

            count = addCount(count, (long) i * (i + 1) / 2);

            for (int j = i + 1; j < nums.length; j ++) {
                while (i >= 0 && nums[i] + nums[j] > target) {
                    i --;
                }

                if (i < 0) break;
                count  = addCount(count, i + 1);
            }
            return (int)count;
        }

        @WatchTime(methodDesc = "双指针")
        public int purchasePlans2(int[] nums, int target) {
            Arrays.sort(nums);
            int left = 0;
            int right = nums.length - 1;
            int ans = 0;
            while (left < right) {
                if (nums[left] + nums[right] > target) {
                    right --;
                } else {
                    ans += right - left;
                    ans %= MOD;
                    left ++;
                }
            }
            return ans;
        }
    }
}
