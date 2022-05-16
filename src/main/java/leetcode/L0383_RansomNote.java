package leetcode;

/**
 * @author : zhaochengming
 * @date : 2022/5/16 0:28
 * @description : https://leetcode.cn/problems/ransom-note/
 */
public class L0383_RansomNote {

    static class Solution {
        public boolean canConstruct(String ransomNote, String magazine) {
            int[] arr = new int[26];
            for (char c: ransomNote.toCharArray()) {
                arr[c - 'a'] ++;
            }

            int diff = 0;
            for (int cnt: arr) {
                if (cnt > 0) diff ++;
            }

            for (char x: magazine.toCharArray()) {
                arr[x - 'a'] --;
                if (arr[x - 'a'] == 0) {
                    diff --;
                    if (diff == 0) return true;
                }
            }

            return false;

        }
    }
}
