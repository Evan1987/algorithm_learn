package labuladong.chap02;

import utils.annotations.WatchTime;
import java.util.*;

/**
 * @author : zhaochengming
 * @date : 2022/4/22 16:26
 * @description : 按键组合，显示最多的A
 */
public class MaxA {

    private static int max(int... nums) {
        int m = nums[0];
        for (int i = 1; i < nums.length; i ++) {
            if (m < nums[i]) {
                m = nums[i];
            }
        }
        return m;
    }

    /**
     * 暴力dp，输出A数量
     * @param n: 剩余操作数
     * @param num: 屏幕已显示数量
     * @param copy: 剪切板存储数量
     * @param memo: 子问题缓存
     * */
    private static int dp1(int n, int num, int copy, Map<String, Integer> memo) {
        if (n <= 0) {
            return num;
        }

        String key = n + "_" + num + "_" + copy;
        Integer res = memo.get(key);
        if (res != null) {
            return res;
        }

        res = max(
                dp1(n - 1, num + 1, copy, memo),         // 输入1个A
                dp1(n - 1, num + copy, copy, memo),      // ctrl+v, 粘贴一次
                dp1(n - 2, num, num, memo));                  // ctrl+a, ctrl+c copy一次（必须成对操作）
        memo.put(key, res);
        return res;
    }

    @WatchTime(methodDesc = "Brute")
    public static int solve1(int n) {
        Map<String, Integer> memo = new HashMap<>();
        return dp1(n, 0, 0, memo);
    }


    @WatchTime(methodDesc = "Optimized")
    public static int solve2(int n) {
        // 最后一次操作不是A，就是ctrl+v

        // dp[i] 第i次操作后最多显示A数量
        int[] dp = new int[n + 1];

        // base case:
        dp[0] = 0;

        for (int i = 1; i <= n; i ++) {
            // 输入一个A
            dp[i]  = dp[i - 1] + 1;
            // 粘贴一个A
            for (int j = 2; j < i; j ++) {
                // j 粘贴动作, j - 2、j - 1 全选复制动作
                // (i - j + 1) 可以发生多少次这样的动作
                dp[i] = Math.max(dp[i], dp[j - 2] * (i - j + 1));
            }
        }

        return dp[n];
    }


    public static void main(String[] args) {
        int n1 = 3;
        System.out.println(solve1(n1));
        System.out.println(solve2(n1));

        int n2 = 5;
        System.out.println(solve1(n2));
        System.out.println(solve2(n2));
    }
}
