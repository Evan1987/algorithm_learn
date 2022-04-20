package labuladong.chap02;

/**
 * @author : zhaochengming
 * @date : 2022/4/20 21:09
 * @description : 最小插入次数构建回文串
 */
public class MinInsertion {

    public static int solve(String s) {
        int n = s.length();

        // dp[i][j] 表示字符串 s[i...j]构建回文串的最小插入次数
        int[][] dp = new int[n][n];

        // 对角线及i > j部分为 0
        for (int i = 0; i < n; i ++) {
            for (int j = 0; j <= i; j ++) {
                dp[i][j] = 0;
            }
        }
        // 根据状态转移依赖，需要左下三个元素，只能选择斜着遍历或者反着遍历
        // 此处斜着遍历上三角
        for (int d = 1; d < n; d ++) {
            for (int i = 0; i + d < n; i ++) {
                int j = i + d;
                if (s.charAt(i) == s.charAt(j)) {
                    dp[i][j] = dp[i + 1][j - 1];
                } else {
                    dp[i][j] = Math.min(dp[i + 1][j] + 1, dp[i][j - 1] + 1);
                }
            }
        }

        return dp[0][n - 1];
    }

    public static void main(String[] args) {
        String s = "abcea";
        System.out.println(solve(s));
    }
}
