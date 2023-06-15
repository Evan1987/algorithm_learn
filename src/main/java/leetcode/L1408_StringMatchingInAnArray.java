package leetcode;

import java.util.*;

/**
 * @author : zhaochengming
 * @date : 2023/6/15 16:33
 * @description : https://leetcode.cn/problems/string-matching-in-an-array/
 * 数组中的字符串匹配
 */
public class L1408_StringMatchingInAnArray {
    static class Solution {
        public List<String> stringMatching(String[] words) {
            List<String> ans = new LinkedList<>();
            Arrays.sort(words, Comparator.comparingInt(String::length));
            for (int i = 0; i < words.length - 1; i ++) {
                for (int j = i + 1; j < words.length; j ++) {
                    if (words[j].contains(words[i])) {
                        ans.add(words[i]);
                        break;
                    }
                }
            }

            return ans;
        }

    }
}
