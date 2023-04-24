package leetcode;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author : zhaochengming
 * @date : 2023/4/24 16:54
 * @description : https://leetcode.cn/problems/uncommon-words-from-two-sentences/
 */
public class L0884_UncommonWordsFromTwoSentences {
    static class Solution {
        public String[] uncommonFromSentences(String s1, String s2) {
            Map<String, Integer> freq = new HashMap<>();
            countWord(s1, freq);
            countWord(s2, freq);

            List<String> ans = new LinkedList<>();
            for (Map.Entry<String, Integer> e: freq.entrySet()) {
                if (e.getValue() == 1) {
                    ans.add(e.getKey());
                }
            }

            return ans.toArray(new String[0]);
        }

        private void countWord(String s, Map<String, Integer> freq) {
            for (String word: s.split(" ")) {
                freq.put(word, freq.getOrDefault(word, 0) + 1);
            }
        }

    }
}
