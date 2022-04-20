package labuladong.chap01;

import java.util.*;

/**
 * @author : zhaochengming
 * @date : 2022/4/20 11:02
 * @description : 滑动窗口，寻找异位词，在字符串S中寻找所有字符串t的异位词，返回起始索引
 */
public class Anagrams {

    private static <T> void addCount(Map<T, Integer> m, T key, int count) {
        m.put(key, m.getOrDefault(key, 0) + count);
    }

    public static List<Integer> solve(String s, String t) {
        List<Integer> res = new LinkedList<>();

        Map<Character, Integer> needs = new HashMap<>();
        for (char c: t.toCharArray()) {
            addCount(needs, c, 1);
        }

        int valid = 0;
        int left = 0, right = 0;
        Map<Character, Integer> window = new HashMap<>();

        while (right < s.length()) {
            char c = s.charAt(right);
            right ++;

            if (needs.containsKey(c)) {
                addCount(window, c, 1);
                if (window.get(c).equals(needs.get(c))) {
                    valid ++;
                }
            }

            // 为了保证是t的排列，必须保证找到的子串长度与t一致
            // 因此该问题的区间收缩时机为区间长度超过了t
            while (right - left >= t.length()) {
                if (valid == needs.size()) {
                    res.add(left);
                }

                // 剔除左侧冗余字符，滑动
                char d = s.charAt(left);
                left ++;
                if (needs.containsKey(d)) {
                    addCount(window, d, -1);
                    if (window.get(d) < needs.get(d)) {
                        valid --;
                    }
                }
            }
        }

        return res;
    }

    public static void main(String[] args) {
        String s1 = "cbaebabacd";
        String s2 = "cbbaebabacd";
        String t = "abc";
        for (Integer i: solve(s1, t)) {
            System.out.println(i);
        }

        for (Integer i: solve(s2, t)) {
            System.out.println(i);
        }
    }
}
