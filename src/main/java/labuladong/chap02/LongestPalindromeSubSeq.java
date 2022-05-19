package labuladong.chap02;


/**
 * @author : zhaochengming
 * @date : 2022/4/20 19:37
 * @description : 最长回文子序列
 */
public class LongestPalindromeSubSeq {

    public static int solve(String s) {
        int n = s.length();

        // dp[i][j] 代表 s[i....j]最长回文子序列长度
        int[][] dp = new int[n][n];

        // base case 对角线为 1, i > j部分为 0
        for (int i = 0; i < n; i ++) {
            dp[i][i] = 1;
        }

        // 根据状态转移依赖，需要左下三个元素，只能选择斜着遍历或者反着遍历
        // 此处斜着遍历上三角
        for (int d = 1; d < n; d ++) {
            for (int i = 0; i + d < n; i ++) {
                int j = i + d;
                // dp[i][j] 与 dp[i + 1][j - 1]之间的关系
                if (s.charAt(i) == s.charAt(j)) {
                    dp[i][j] = dp[i + 1][j - 1] + 2;
                } else {
                    dp[i][j] = Math.max(dp[i + 1][j], dp[i][j - 1]);
                }
            }
        }

        return dp[0][n - 1];
    }

    public static void main(String[] args) {
        String s = "aecda";
        System.out.println(solve(s));
    }
}
