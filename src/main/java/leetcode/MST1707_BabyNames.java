package leetcode;

import java.util.*;

/**
 * @author : zhaochengming
 * @date : 2022/5/15 14:33
 * @description : https://leetcode.cn/problems/baby-names-lcci/
 */
public class MST1707_BabyNames {
    static class Solution {
        public String[] trulyMostPopular(String[] names, String[] synonyms) {
            Map<String, String> synonymMap = new HashMap<>();
            for (String synonym: synonyms) {
                String[] pair = synonym.substring(1, synonym.length() - 1).split(",");
                String left = pair[0];
                String right = pair[1];
                while (synonymMap.containsKey(left)) {
                    left = synonymMap.get(left);
                }

                while (synonymMap.containsKey(right)) {
                    right = synonymMap.get(right);
                }

                int comp = left.compareTo(right);
                if (comp < 0) {
                    synonymMap.put(right, left);
                } else if (comp > 0) {
                    synonymMap.put(left, right);
                }
            }

            Map<String, Integer> frequency = new HashMap<>();
            for (String summary: names) {
                int i = summary.indexOf('(');
                String name = summary.substring(0, i);
                while (synonymMap.containsKey(name)) {
                    name = synonymMap.get(name);
                }
                int freq = Integer.parseInt(summary.substring(i + 1, summary.length() - 1));
                frequency.put(name, freq + frequency.getOrDefault(name, 0));
            }

            String[] res = new String[frequency.size()];
            int i = 0;
            for (Map.Entry<String, Integer> nameCnt: frequency.entrySet()) {
                res[i ++] = nameCnt.getKey() + "(" + nameCnt.getValue() + ")";
            }
            return res;
        }
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        String[] names = {"John(15)","Jon(12)","Chris(13)","Kris(4)","Christopher(19)"};
        String[] synonyms = {"(Jon,John)","(John,Johnny)","(Chris,Kris)","(Chris,Christopher)"};
        String[] res = sol.trulyMostPopular(names, synonyms);
        for (String count: res) {
            System.out.println(count);
        }
    }
}
