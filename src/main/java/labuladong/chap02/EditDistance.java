package labuladong.chap02;

/**
 * @author : zhaochengming
 * @date : 2022/4/20 15:39
 * @description : 字符串编辑距离
 */
public class EditDistance {

    private static int min(int... nums) {
        int res = Integer.MAX_VALUE;
        for (int x: nums) {
            res = Math.min(res, x);
        }
        return res;
    }

    public static int solve(String s1, String s2) {
        int m = s1.length(), n = s2.length();

        // dp[i][j] 表示 s1[:i] 与 s2[:j] 编辑距离，首行和首列补空串计算
        int[][] dp = new int[m + 1][n + 1];
        // base case: 首列，对应s2为空串，则编辑距离随着s1增长而增长
        for (int i = 0; i <= m; i ++) {
            dp[i][0] = i;
        }
        // base case: 首行，对应s1为空串，则编辑距离随着s2增长而增长
        for (int i = 0; i <= n; i ++) {
            dp[0][i] = i;
        }

        for (int i = 1; i <= m; i ++) {
            for (int j = 1; j <= n; j ++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = min(
                            dp[i][j - 1] + 1,   // s1插入和s2[j]一样的字符，前移j继续比较
                            dp[i - 1][j] + 1,           // s1删除s1[i]，前移i继续比较
                            dp[i - 1][j - 1] + 1);      // 替换s1[i]为s2[j]，前移i，j继续比较
                }
            }
        }

        return dp[m][n];
    }

    // 打印具体操作
    public static int solve2(String s1, String s2) {
        int m = s1.length(), n = s2.length();

        Node[][] dp = new Node[m + 1][n + 1];
        // base case: 首列，对应s2为空串，则编辑距离随着s1增长而增长，s1删除
        for (int i = 0; i <= m; i ++) {
            dp[i][0] = new Node(i, Choice.DELETE);
        }
        // base case: 首行，对应s1为空串，则编辑距离随着s2增长而增长, s2插入
        for (int i = 0; i <= n; i ++) {
            dp[0][i] = new Node(i, Choice.INSERT);
        }

        for (int i = 1; i <= m; i ++) {
            for (int j = 1; j <= n; j ++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    Node node = dp[i - 1][j - 1];
                    dp[i][j] = new Node(node.distance, Choice.IGNORE);
                } else {
                    Node insert = dp[i][j - 1];
                    Node delete = dp[i - 1][j];
                    Node replace = dp[i - 1][j - 1];
                    Node res = new Node(insert.distance, Choice.INSERT);
                    if (res.distance > delete.distance) {
                        res.distance = delete.distance;
                        res.choice = Choice.DELETE;
                    }

                    if (res.distance > replace.distance) {
                        res.distance = replace.distance;
                        res.choice = Choice.REPLACE;
                    }

                    res.distance ++;

                    dp[i][j] = res;
                }
            }
        }
        printResult(dp, s1, s2);
        return dp[m][n].distance;
    }

    // 打印操作
    private static void printResult(Node[][] dp, String s1, String s2) {
        int rows = dp.length, cols = dp[0].length;
        // 回溯
        int i = rows - 1, j = cols - 1;
        System.out.println("Change s1=" + s1 + " to s2=" + s2);

        while (i > 0 && j > 0) {
            char c1 = s1.charAt(i - 1), c2 = s2.charAt(j - 1);
            Choice choice = dp[i][j].choice;
            System.out.print("s1[" + (i - 1) + "]:");
            switch (choice) {
                case IGNORE:
                    // 忽略，两个指针同时前进
                    System.out.println("skip '" + c1 + "'");
                    i --;
                    j --;
                    break;
                case INSERT:
                    // 将s2[j]插入s1[i]，则s2指针前进
                    System.out.println("insert '" + c2 + "'");
                    j --;
                    break;
                case DELETE:
                    // 将s1[i]删除，则s1指针前进
                    System.out.println("delete '" + c1 + "'");
                    i --;
                    break;
                case REPLACE:
                    // s[i]替换成s[j]则两个指针同时前进
                    System.out.println("replace '" + c1 + "' with '" + c2 + "'");
                    i --;
                    j --;
                    break;
            }
        }

        // s1指针未走完
        while (i > 0) {
            System.out.print("s1[" + (i - 1) + "]:");
            System.out.println("delete '" + s1.charAt(i - 1) + "'");
            i --;
        }

        // s2指针未走完
        while (j > 0) {
            System.out.print("s1[0]:");
            System.out.println("insert '" + s2.charAt(j - 1) + "'");
            j --;
        }
    }

    private enum Choice {

        IGNORE(0, "无操作"),
        INSERT(1, "插入"),
        DELETE(2, "删除"),
        REPLACE(3, "替换"),
        ;

        int code;
        String action;

        Choice(int code, String action) {
            this.code = code;
            this.action = action;
        }
    }

    private static class Node {
        int distance;
        Choice choice;

        Node(int distance, Choice choice) {
            this.distance = distance;
            this.choice = choice;
        }
    }

    public static void main(String[] args) {
        String s1 = "intention";
        String s2 = "execution";
        System.out.println(solve(s1, s2));

        System.out.println(solve2(s1, s2));
    }
}
