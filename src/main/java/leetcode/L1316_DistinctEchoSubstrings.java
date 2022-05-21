package leetcode;

import java.util.*;

/**
 * @author : zhaochengming
 * @date : 2022/5/20 22:46
 * @description : https://leetcode.cn/problems/distinct-echo-substrings/
 */
public class L1316_DistinctEchoSubstrings {
    static class Solution {

        private static final int base = 31, mod = (int) 1e9 + 7;

        // Rabin-Karp + 前缀和
        public int distinctEchoSubstrings2(String text) {
            int n = text.length();
            if (n == 1) return 0;
            Set<Integer> ans = new HashSet<>();
            int[] pre = new int[n + 1];
            int[] mul = new int[n + 1];
            mul[0] = 1;
            for (int i = 1; i <= n; i ++) {
                pre[i] = (pre[i - 1] * base + text.charAt(i - 1) - 'a') % mod;
                mul[i] = mul[i - 1] * base % mod;
            }

            for (int i = 0; i < n; i ++) {
                for (int j = i + 1; 2 * j - i < n; j ++) {
                    int l = j - i;
                    int leftHash = hash(pre, mul, i, j);
                    if (leftHash == hash(pre, mul, j, j + l)) {
                        ans.add(leftHash);
                    }
                }
            }

            return ans.size();

        }

        private int hash(int[] pre, int[] mul, int l, int r) {
            return (pre[r] - pre[l] * mul[r - l] % mod + mod) % mod;
        }

        // 暴力穷举
        public int distinctEchoSubstrings(String text) {
            int n = text.length();
            if (n == 1) return 0;
            Set<String> ans = new HashSet<>();

            for (int i = 0; i < n; i ++) {
                for (int j = i + 1; 2 * j - i < n; j ++) {
                    if (equals(text, i, j, j - i)) {
                        ans.add(text.substring(i, j));
                    }
                }
            }

            return ans.size();
        }

        private boolean equals(String text, int a1, int a2, int len) {
            for (int i = 0; i < len; i ++) {
                if (text.charAt(a1 + i) != text.charAt(a2 + i)) {
                    return false;
                }
            }

            return true;
        }

    }

    public static void main(String[] args) {
        String text = "leetcodeleetcode";
        Solution sol = new Solution();
        System.out.println(sol.distinctEchoSubstrings(text));
    }
}
