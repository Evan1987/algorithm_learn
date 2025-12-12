package leetcode;


import java.util.*;
/**
 * @author : zhaochengming
 * @date : 2025/12/13 00:01
 * @description : https://leetcode.cn/problems/generate-parentheses/
 * 给出所有有效的括号组合
 */
public class L0022_GenerateParentheses {
    static class Solution {
        @SuppressWarnings("unchecked")
        private final List<String>[] cache = (List<String>[]) new ArrayList[10];

        public List<String> generateParenthesis(int n) {
            return generate(n);
        }

        // 一定是(a)b这样的括号序列组成，其中a或b可以为空
        private List<String> generate(int n) {
            List<String> ans = new ArrayList<>();
            if (n == 0) {
                ans.add("");
            } else {
                if (cache[n] != null) return cache[n];

                for (int c = 0; c <= n - 1; c ++) {
                    for (String a: generate(c)) {
                        for (String b: generate(n - 1 - c)) {
                            ans.add("(" + a + ")" + b);
                        }
                    }
                }
            }
            cache[n] = ans;
            return ans;
        }
    }
}
