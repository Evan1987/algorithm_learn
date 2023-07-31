package leetcode;

/**
 * @author : zhaochengming
 * @date : 2023/7/31 16:13
 * @description : https://leetcode.cn/problems/repeated-substring-pattern/
 * 检查字符串是否由重复子串组成
 */
public class L0459_RepeatedSubStringPattern {
    static class Solution {

        // 假设 S = n * sub （由重复子串组成）
        // 有 S + S = n * sub + n * sub
        // 前后各去掉一个字符: (a) + (n - 1) * sub + (n - 1) * sub + (b) 中间还可以包含一个S
        //
        // 假设 S = s1 + s2 （非重复子串组成, s1 != s2）
        // S + S = s1 + s2 + s1 + s2
        // 前后各去掉一个字符: (a) + s2 + s1 + (b) 中间不包含 S
        public boolean repeatedSubstringPattern(String s) {
            return (s.substring(1) + s.substring(0, s.length() - 1)).contains(s);
        }
    }

    public static void main(String[] args) {
        String s = "abacababacab";
        Solution sol = new Solution();
        sol.repeatedSubstringPattern(s);
    }
}
