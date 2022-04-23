package labuladong.chap02;


/**
 * @author : zhaochengming
 * @date : 2022/4/21 00:48
 * @description :  正则匹配
 */
public class RegexMatch {

    private static boolean isWildCard(String p, int index) {
        return index < p.length() && p.charAt(index) == '*';
    }

    public static boolean match(String s, String pattern) {
        int m = s.length(), n = pattern.length();

        // dp[i][j] 表示 s[i:]是否可以匹配p[j:]， 则最终要输出的是 dp[0][0]
        boolean[][] dp = new boolean[m + 1][n + 1];

        // base case 1: j = n 时（模式串到末尾），则必须有 i == m （匹配串也得到末尾）
        for (int i = 0; i < m; i ++) {
            dp[i][n] = false;
        }
        dp[m][n] = true;

        // base case 2: i = m 时（匹配串走到末尾），则须有 p[j:]可以匹配空串 即 x*y*z*...这种成对出现的情况
        // 是否成对出现，倒着看只要结尾有一个不成对，则前面都是false
        for (int j = n - 1; j > 0; j -= 2) {
            // 奇数肯定不成对
            dp[m][j] = false;
            if (pattern.charAt(j) == '*') {
                dp[m][j - 1] = true;    // 在前一个字母处为 true
            } else {
                for (int k = 0; k < j; k ++) {
                    dp[m][k] = false;
                }
                break;
            }
        }

        // 倒着循环

        for (int i = m- 1; i >= 0; i --) {
            for (int j = n - 1; j >= 0; j --) {
                // s[i] match p[j]
                if (s.charAt(i) == pattern.charAt(j) || pattern.charAt(j) == '.') {

                    // 后面带通配符可以匹配 0次或多次
                    if (isWildCard(pattern, j + 1)) {
                        // dp[i][j + 2] 代表跳过通配符部分，匹配 0次
                        // dp[i + 1][j] 代表p[j]可以继续匹配，匹配多次
                        dp[i][j] = dp[i][j + 2] || dp[i + 1][j];
                    } else {
                        // 不带通配符，只能匹配1次
                        dp[i][j] = dp[i + 1][j + 1];
                    }
                } else {
                    // 首字符不匹配
                    // 后面带通配符要想匹配的话，只能匹配 0次
                    if (isWildCard(pattern, j + 1)) {
                        dp[i][j] = dp[i][j + 2];
                    } else {
                        // 后面不带通配符那不可能匹配
                        dp[i][j] = false;
                    }
                }
            }
        }

        return dp[0][0];
    }

    public static void main(String[] args) {
        String x = "abcd";
        String p = "aba*cd";
        System.out.println(match(x, p));
    }
}
