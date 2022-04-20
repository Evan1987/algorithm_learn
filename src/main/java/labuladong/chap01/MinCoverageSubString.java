package labuladong.chap01;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : zhaochengming
 * @date : 2022/4/19 23:41
 * @description : 滑动窗口问题, 最小覆盖子串，在s中找到覆盖所有target中字母及数量的最短子串
 */
public class MinCoverageSubString {

    private static <T> void addCount(Map<T, Integer> m, T key, int count) {
        m.put(key, m.getOrDefault(key, 0) + count);
    }

    public static String solve(String s, String target) {

        Map<Character, Integer> needs = new HashMap<>();    // target内所需字符统计
        for (char c: target.toCharArray()) {
            addCount(needs, c, 1);
        }

        // 记录最小覆盖串的起始索引和长度
        int start = 0;
        int len = Integer.MAX_VALUE;

        Map<Character, Integer> window = new HashMap<>();   // 窗口内字符统计
        int left = 0, right = 0;                            // 窗口左右界，左闭右开区间
        int valid = 0;                                      // 计算字符的覆盖条件，与needs大小相同时即为满足需求
        while (right < s.length()) {
            char c = s.charAt(right);  // 新增窗口内统计字符
            right ++;

            if (needs.containsKey(c)) {
                addCount(window, c, 1);
                if (window.get(c).equals(needs.get(c))) {
                    valid ++;
                }
            }

            // 满足覆盖条件，同时也是缩减左边界的条件
            while (valid == needs.size()) {
                // 更新最小覆盖子串信息
                if (right - left < len) {
                    start = left;
                    len = right - left;
                }

                // 缩减左边界
                char d = s.charAt(left);  // 要移除的字符
                left ++;

                if (needs.containsKey(d)) {
                    addCount(window, d, -1);
                    if (window.get(d) < needs.get(d)) {
                        valid --;
                    }
                }
            }
        }

        return len == Integer.MAX_VALUE ? "" : s.substring(start, start + len);
    }

    public static void main(String[] args) {
        String s = "ADBECFEBANC";
        String t = "ABC";
        System.out.println(solve(s, t));
    }
}
