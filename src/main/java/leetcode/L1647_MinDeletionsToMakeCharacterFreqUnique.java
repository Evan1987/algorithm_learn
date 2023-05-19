package leetcode;

import java.util.HashSet;
import java.util.Set;

/**
 * @author : zhaochengming
 * @date : 2023/5/19 15:47
 * @description : https://leetcode.cn/problems/minimum-deletions-to-make-character-frequencies-unique/
 * 使字符频次唯一的最小删除次数
 */
public class L1647_MinDeletionsToMakeCharacterFreqUnique {
    static class Solution {
        public int minDeletions(String s) {
            int[] counts = new int[26];
            for (char c: s.toCharArray()) {
                counts[c - 'a'] ++;
            }

            int ans = 0;
            Set<Integer> sets = new HashSet<>();
            for (int num: counts) {
                while (num > 0 && !sets.add(num)) {
                    num --;
                    ans ++;
                }
            }

            return ans;
        }
    }
}
