package labuladong.chap02;

import utils.annotations.WatchTime;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : zhaochengming
 * @date : 2022/4/22 17:01
 * @description : 高楼扔鸡蛋，N层楼、K个鸡蛋，试验恰好摔不碎鸡蛋的最高层楼，计算最坏情况需要扔多少次鸡蛋。
 */
public class EggDrop {

    /**
     * 输出最坏情况下的最少实验次数
     * @param K 鸡蛋个数
     * @param N 楼层数，可以为0
     * @param memo 子问题缓存
     * */
    private static int dp1(int K, int N, Map<String, Integer> memo) {

        // base case:
        // 只有0层楼，显然不需要扔鸡蛋
        // 只有1个鸡蛋，显然必须一层层扔
        if (N == 0) return 0;
        if (K == 1) return N;

        String key = K + "_" + N;
        Integer res = memo.get(key);
        if (res != null) {
            return res;
        }

        // 最坏情况下的最少实验次数
        res = Integer.MAX_VALUE;
        for (int i = 1; i <= N; i ++) {
            // 最坏情况
            int x = Math.max(
                    dp1(K - 1, i - 1, memo),    // 在第i层实验时碎了，则需要在i - 1层（含）及以下搜索
                    dp1(K, N - i, memo)) + 1;      // 在第i层实验时没碎，则需要在i层以上搜索
            // 最少次数
            res = Math.min(res, x);
        }
        memo.put(key, res);

        return res;
    }

    // 优化搜索min(max)部分，即最坏情况下的最少实验次数
    // 寻找 碎了dp(K - 1, i - 1) 与 没碎dp(K, N - i) 两个对i的单调函数交点问题
    // 在K、N一定时，dp(K - 1, i - 1)随i增大而增大， dp(K, N - i)随i增大而减小
    // 山谷值搜索问题，注意谷值所在点对应i不一定是整数，需要防止死循环
    private static int dp2(int K, int N, Map<String, Integer> memo) {

        if (N == 0) return 0;
        if (K == 1) return N;

        String key = K + "_" + N;
        Integer res = memo.get(key);
        if (res != null) {
            return res;
        }

        // 最坏情况下的最少实验次数
        // 找交点
        int lo = 1, hi = N;
        while (hi - lo > 1) {
            int mid = lo + (hi - lo) / 2;    //  -> i
            int broken = dp2(K - 1, mid - 1, memo);
            int noBroken = dp2(K, N - mid, memo);

            if (broken > noBroken) {
                hi = mid;
            } else if (broken < noBroken) {
                lo = mid;
            } else {
                lo = hi = mid;
            }
        }

        // lo, hi在交点两侧
        if (lo != hi) {
            res = Math.min(
                    Math.max(dp2(K - 1, lo - 1, memo), dp2(K, N - lo, memo)),
                    Math.max(dp2(K - 1, hi - 1, memo), dp2(K, N - hi, memo))) + 1;
        } else {
            // lo, hi就是交点
            res = dp2(K - 1, lo - 1, memo) + 1;
        }

        memo.put(key, res);

        return res;
    }


    // 修改dp设定，变为给定K和次数m，最坏可以确定多高的楼层
    public static int dp3(int K, int N) {
        // m 最多不会超过N次（线性扫描）
        int[][] dp = new int[K + 1][N + 1];

        // base case： K = 0时均为0， m = 0时均为0
        // java数组初始化均为0 略过
        int m = 0;
        while (dp[K][m] < N) {
            m ++;
            for (int k = 1; k <= K; k ++) {
                // 在某层实验，摔碎了能确定该层以下的层数dp[K - 1][m - 1]，没摔碎则能确定该层以上的层数dp[K][m - 1]
                // 加上该层则为dp[K][m]能确定的层数
                dp[k][m] = dp[k - 1][m - 1] + dp[k][m - 1] + 1;
            }
        }
        return m;
    }

    @WatchTime(methodDesc = "loop search")
    public static int solve1(int K, int N) {
        Map<String, Integer> memo = new HashMap<>();
        return dp1(K, N, memo);
    }

    @WatchTime(methodDesc = "binary search")
    public static int solve2(int K, int N) {
        Map<String, Integer> memo = new HashMap<>();
        return dp2(K, N, memo);
    }

    @WatchTime(methodDesc = "another vision")
    public static int solve3(int K, int N) {
        return dp3(K, N);
    }

    public static void main(String[] args) {
        System.out.println(solve1(2, 100));
        System.out.println(solve2(2, 100));
        System.out.println(solve3(2, 100));

        System.out.println(solve1(3, 15));
        System.out.println(solve2(3, 15));
        System.out.println(solve3(3, 15));
    }
}
