package labuladong.chap04;

import utils.annotations.WatchTime;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : zhaochengming
 * @date : 2022/5/5 09:34
 * @description : 和为k的子数组
 */
public class SubArraySum {

    // 前缀数组 cumsum
    @WatchTime(methodDesc = "cumsum array")
    public static int solve1(int[] nums, int k) {
        int n = nums.length;
        // cumsum[i] 代表前i项和
        int[] cumsum = new int[n + 1];
        cumsum[0] = 0;
        for (int i = 0; i < n; i ++) {
            cumsum[i + 1] = cumsum[i] + nums[i];
        }

        int ans = 0;
        // 遍历所有 nums[i...j)的和
        for (int i = 0; i < n; i ++) {
            for (int j = i + 1; j <= n; j ++) {
                if (cumsum[j] - cumsum[i] == k) {
                    ans ++;
                }
            }
        }
        return ans;
    }

    @WatchTime(methodDesc = "hashmap")
    public static int solve2(int[] nums, int k) {
        int n = nums.length;
        Map<Integer, Integer> preSum = new HashMap<>();
        preSum.put(0, 1);

        int ans = 0, sum0_i = 0;
        for (int i = 0; i < n; i ++) {
            sum0_i += nums[i];

            int sum0_j = sum0_i - k;
            if (preSum.containsKey(sum0_j)) {
                ans += preSum.get(sum0_j);
            }

            preSum.put(sum0_i, preSum.getOrDefault(sum0_i, 0) + 1);
        }

        return ans;
    }

    public static void main(String[] args) {
        int[] nums = {3, 5, 2, -2, 4, 1};
        int k = 5;
        System.out.println(solve1(nums, k));
        System.out.println(solve2(nums, k));
    }
}
