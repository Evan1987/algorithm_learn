package leetcode;



import java.util.*;

/**
 * @author : zhaochengming
 * @date : 2022/5/16 0:11
 * @description : https://leetcode.cn/problems/valid-anagram/
 */
public class L0242_ValidAnagram {
    static class Solution {
        public boolean isAnagram(String s, String t) {
            if (s.length() != t.length()) return false;
            Map<Character, Integer> sMap = characterCount(s);
            Map<Character, Integer> tMap = characterCount(t);

            for (Map.Entry<Character, Integer> e: sMap.entrySet()) {
                if (!e.getValue().equals(tMap.get(e.getKey()))) return false;
            }
            return true;
        }

        private Map<Character, Integer> characterCount(String x) {
            Map<Character, Integer> summary = new HashMap<>();
            for (char c: x.toCharArray()) {
                summary.put(c, summary.getOrDefault(c, 0) + 1);
            }
            return summary;
        }
    }
}
