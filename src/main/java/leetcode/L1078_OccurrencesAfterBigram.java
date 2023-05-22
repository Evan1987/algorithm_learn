package leetcode;

import java.util.LinkedList;
import java.util.List;

/**
 * @author : zhaochengming
 * @date : 2023/5/22 16:49
 * @description : https://leetcode.cn/problems/occurrences-after-bigram/
 */
public class L1078_OccurrencesAfterBigram {
    static class Solution {
        public String[] findOcurrences(String text, String first, String second) {
            String[] words = text.split(" ");
            int n = words.length;

            List<String> ans = new LinkedList<>();
            for (int i = 0; i < n - 2; i ++) {
                if (words[i].equals(first) && words[i + 1].equals(second)) {
                    ans.add(words[i + 2]);
                }
            }

            return ans.toArray(new String[0]);
        }
    }
}
