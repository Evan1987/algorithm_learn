package leetcode;

import java.util.*;

/**
 * @author : zhaochengming
 * @date : 2023/5/5 14:26
 * @description :https://leetcode.cn/problems/t9-lcci/
 * 模拟9键键盘输出
 */
public class MST1620_T9KeyBoard {
    static class Solution {
        static String[] keyboard = {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
        static Map<Character, Integer> characterMap = new HashMap<>();
        static {
            for (int i = 2; i < keyboard.length; i ++) {
                for (char x: keyboard[i].toCharArray()) {
                    characterMap.put(x, i);
                }
            }
        }

        public List<String> getValidT9Words(String num, String[] words) {
            List<String> ans = new LinkedList<>();
            int n = num.length();

            for (String word: words) {
                boolean flag = true;
                for (int i = 0; i < n; i ++) {
                    if (characterMap.get(word.charAt(i)) != num.charAt(i) - '0') {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    ans.add(word);
                }
            }

            return ans;
        }
    }
}
