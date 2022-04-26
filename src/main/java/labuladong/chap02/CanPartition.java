package labuladong.chap02;

/**
 * @author : zhaochengming
 * @date : 2022/4/25 14:53
 * @description :
 */
public class CanPartition {

    // 转化为是否有种组合可以正好装满背包
    public static boolean solve(int[] nums) {
        int n = nums.length;

        int W = 0;
        for (int x: nums) {
            W += x;
        }

        // 非偶数直接不可能
        if (W % 2 != 0) {
            return false;
        }

        W /= 2;   // 背包容量
        // dp[i][w] 前i个商品是否有方法可以正好装满w.
        boolean[][] dp = new boolean[n + 1][W + 1];

        // base case: dp[0][.] = false, dp[.][0] = true
        for (int i = 0; i <= n; i ++) {
            dp[i][0] = true;
        }

        for (int i = 1; i <= n; i ++) {
            for (int w = 1; w <= W; w ++) {
                if (w < nums[i - 1]) {
                    dp[i][w] = dp[i - 1][w];
                } else {
                    // dp[i - 1][w] 前i - 1个商品有办法填满 w，则对于 i - 1商品可以不装
                    // dp[i - 1][w - nums[i - 1]] 前 i - 1商品可以装满 w - nums[i - 1]，则装上 i - 1商品正好可以装满 dp[i][w]
                    dp[i][w] = dp[i - 1][w] || dp[i - 1][w - nums[i - 1]];
                }
            }
        }

        return dp[n][W];
    }

    // 压缩状态
    public static boolean solve2(int[] nums) {
        int n = nums.length;

        int W = 0;
        for (int x: nums) {
            W += x;
        }

        // 非偶数直接不可能
        if (W % 2 != 0) {
            return false;
        }

        W /= 2;   // 背包容量
        // 原来二维dp上的1行
        boolean[] dp = new boolean[W + 1];
        // base case : 对应dp[.][0]
        dp[0] = true;

        for (int i = 0; i < n; i ++) {
            // dp[i][w] = dp[i - 1][w] || dp[i - 1][w - nums[i - 1]];
            // 更新取决于上一行的左侧数据，因此降为1维时，要保证数据不受污染，先更新右侧的
            for (int w = W; w >= 1; w --) {
                if (w >= nums[i]) {
                    dp[w] = dp[w] || dp[w - nums[i]];
                }
            }
        }

        return dp[W];
    }

    public static void main(String[] args) {
        int[] nums1 = {1, 5, 11, 5};
        int[] nums2 = {2, 3, 2, 5};
        System.out.println(solve(nums1));
        System.out.println(solve(nums2));

        System.out.println(solve2(nums1));
        System.out.println(solve2(nums2));
    }
}
