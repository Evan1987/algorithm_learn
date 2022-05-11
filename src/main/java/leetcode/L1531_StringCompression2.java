package leetcode;

import java.util.*;

/**
 * @author : zhaochengming
 * @date : 2022/5/9 22:29
 * @description : https://leetcode.cn/problems/string-compression-ii/
 * todo unfinished
 */
public class L1531_StringCompression2 {

    static class Solution {

        // 不同count下的编码长度
        private int length(int count) {
            if (count == 0) return 0;
            if (count == 1) return 1;
            if (count <= 9) return 2;
            if (count <= 99) return 3;
            return 4;
        }


        public int getLengthOfOptimalCompression(String s, int k) {
            int n = s.length();
            if (n - k <= 2) return n - k;

            // 变成 连续的片段，各段长度
            List<int[]> summary = new ArrayList<>();
            for (int i = 0; i < n; ) {
                int j = i;
                while (j < n && s.charAt(i) == s.charAt(j)) {
                    j ++;
                }
                summary.add(new int[]{s.charAt(i), j - i});
                i = j;
            }

            int nSeg = summary.size();
            // 累积长度
            int[] cumLength = new int[nSeg + 1];
            cumLength[0] = 0;
            for (int i = 1; i <= nSeg; i ++) {
                cumLength[i] = cumLength[i - 1] + summary.get(i - 1)[1];
            }

            // dp[i][j] 前 i 段删除 j 个字符的最小编码长度
            int[][] dp = new int[nSeg + 1][k + 1];
            for (int i = 0; i <= nSeg; i ++) {
                Arrays.fill(dp[i], n);
            }
            dp[0][0] = 0;

            for (int i = 1; i <= nSeg; i ++) {
                for (int j = 0; j <= k; j ++) {
                    if (j >= cumLength[i]) {
                        dp[i][j] = 0;
                        continue;
                    }
                    // 第i段长度
                    int segLength = summary.get(i)[1];
                    // 第i段删除t个，前i-1段删除 j-t个，**且不涉及与第i段合并**
                    for (int t = 0; t <= Math.min(j, segLength); t ++) {
                        // 前 i-1段删除j-t个 dp[i - 1][j - t]
                        // 第i段删除t个 length(segLength - t)
                        dp[i][j] = Math.min(dp[i][j], dp[i - 1][j - t] + length(segLength - t));
                    }

                    // 在前i-1段删j个，**且涉及与第i段合并(那一定是中间某段被完整删掉了)**
                    int tr = 0;
                    for (int t = i - 1; t >= 1; t --) {
                        // cumLength[i] 包含 0 ... (i - 1)段
                        // 删掉 t段至第i段的全部
                        if (j >= cumLength[i] - cumLength[t]) {
                            dp[i][j] = Math.min(dp[i][j], dp[t][j - (cumLength[i] - cumLength[t])]);
                        }
                        if (summary.get(t)[0] == summary.get(i)[0]) {
                            segLength += summary.get(t)[1];
                            for (int u = tr; u < segLength + tr; u ++){
                                if (j < u) break;
                                dp[i][j] = Math.min(dp[i][j], dp[t - 1][j - u] + length(segLength - (u - tr)));
                            }
                        }
                    }
                }
            }


            return dp[nSeg][k];
        }
    }
}
