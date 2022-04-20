package labuladong.chap01;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : zhaochengming
 * @date : 2022/4/20 11:32
 * @description : 滑动窗口，最长无重复字符子串
 */
public class LongestUniqueSubString {

    private static <T> void addCount(Map<T, Integer> m, T key, int count) {
        m.put(key, m.getOrDefault(key, 0) + count);
    }

    public static String solve(String s) {
        int left = 0, right = 0;
        Map<Character, Integer> window = new HashMap<>();

        String res = "";
        int len = 0;

        while (right < s.length()) {
            char c = s.charAt(right);
            right ++;
            addCount(window, c, 1);

            // 出现重复字符，需要缩减
            while (window.get(c) > 1) {
                char d = s.charAt(left);
                left ++;
                addCount(window, d, -1);
            }

            // 更新结果
            if (right - left > len) {
                len = right - left;
                res = s.substring(left, right);
            }
        }

        return res;
    }

    public static void main(String[] args) {
        String s = "aabab";
        System.out.println(solve(s));
    }

}
