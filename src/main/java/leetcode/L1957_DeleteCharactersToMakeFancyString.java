package leetcode;


/**
 * @author : zhaochengming
 * @date : 2022/5/25 18:10
 * @description : https://leetcode.cn/problems/delete-characters-to-make-fancy-string/
 */
public class L1957_DeleteCharactersToMakeFancyString {
    static class Solution {
        public String makeFancyString(String s) {
            int count = 0;
            char curr = '.';
            int index = 0;
            char[] res = new char[s.length()];

            for (char c: s.toCharArray()) {
                if (c == curr) {
                    count ++;
                    if (count == 3) {
                        count --;
                        continue;
                    }
                } else {
                    count = 1;
                }

                curr = c;
                res[index ++] = c;
            }

            return new String(res).substring(0, index);
        }
    }
}
