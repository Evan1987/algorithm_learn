package leetcode;

import java.util.*;

/**
 * @author : zhaochengming
 * @date : 2023/5/9 10:58
 * @description : https://leetcode.cn/problems/count-substrings-with-only-one-distinct-letter/
 * 统计只含单一字母的子串
 */
public class L1180_CountSubStrWithSingleLetter {
    static class Solution {
        public int countLetters(String s) {
            int ans = 0;
            char prev = s.charAt(0);
            int length = 1;
            for (int i = 1; i < s.length(); i ++) {
                char curr = s.charAt(i);
                if (curr == prev) {
                    length ++;
                } else {
                    ans += (length + 1) * length / 2 ;
                    length = 1;
                    prev = curr;
                }
            }
            ans += (length + 1) * length / 2 ;
            return ans;
        }
    }
}
