package leetcode;

import java.util.*;

/**
 * @author : zhaochengming
 * @date : 2022/5/27 11:57
 * @description : https://leetcode.cn/problems/find-closest-lcci/
 */
public class MST1711_FindClosest {
    static class Solution {
        public int findClosest(String[] words, String word1, String word2) {
            Map<String, List<Integer>> indexes = new HashMap<>();
            for (int i = 0; i < words.length; i ++) {
                String word = words[i];
                indexes.putIfAbsent(word, new ArrayList<>());
                indexes.get(word).add(i);
            }

            List<Integer> a = indexes.get(word1), b = indexes.get(word2);
            int ans = Integer.MAX_VALUE;
            int pa = 0, pb = 0;

            while (pa < a.size() && pb < b.size()) {
                int indexA = a.get(pa), indexB = b.get(pb);
                if (indexB > indexA) {
                    pa ++;
                } else {
                    pb ++;
                }
                ans = Math.min(ans, Math.abs(indexA - indexB));
                if (ans == 1) return 1;
            }
            return ans;
        }

        public int findClosest2(String[] words, String word1, String word2) {
            int pa = -1, pb = -1;
            int ans = Integer.MAX_VALUE;
            for (int i = 0; i < words.length; i ++) {
                if (words[i].equals(word1)) {
                    pa = i;
                } else if (words[i].equals(word2)) {
                    pb = i;
                } else {
                    continue;
                }

                if (pa >= 0 && pb >= 0) {
                    ans = Math.min(ans, Math.abs(pb - pa));
                    if (ans == 1) return 1;
                }
            }
            return ans;
        }
    }

}
