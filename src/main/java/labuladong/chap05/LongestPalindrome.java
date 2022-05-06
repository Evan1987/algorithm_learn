package labuladong.chap05;

/**
 * @author : zhaochengming
 * @date : 2022/5/6 11:18
 * @description : 最长回文串
 */
public class LongestPalindrome {

    // 找到以 s[left..right]为中心的回文串，当left == right时长度为奇数， 否则为偶数
    private static String findPalindrome(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left --;
            right ++;
        }

        return s.substring(left + 1, right);
    }

    public static String solve1(String s) {

        String res = "";
        for (int i = 0; i < s.length(); i ++) {
            String s1 = findPalindrome(s, i, i);
            String s2 = findPalindrome(s, i, i + 1);
            res = res.length() < s1.length() ? s1 : res;
            res = res.length() < s2.length() ? s2 : res;
        }

        return res;
    }

    // dp方法
    public static String solve2(String s) {
        int n = s.length();

        // dp[i][j] 代表s[i...j]是否是回文串
        boolean[][] dp = new boolean[n][n];
        // base case dp[i][i] = true
        for (int i = 0; i < n; i ++) {
            dp[i][i] = true;
        }

        // 倒着遍历
        int left, right, length;
        left = right = length = 0;

        for (int i = n - 2; i >= 0; i --) {
            for (int j = i + 1; j < n; j ++) {
                if (s.charAt(j) == s.charAt(i)) {
                    dp[i][j] = j - 1 < i + 1 || dp[i + 1][j - 1];

                    if (dp[i][j] && j - i + 1 > length) {
                        left = i; right = j; length = j - i + 1;
                    }
                }
            }
        }

        return s.substring(left, right + 1);

    }

    public static void main(String[] args) {
        String s1 = "acabx";
        String s2 = "aacxycaa";
        System.out.println(solve1(s1));
        System.out.println(solve2(s1));

        System.out.println(solve1(s2));
        System.out.println(solve2(s2));
    }
}
