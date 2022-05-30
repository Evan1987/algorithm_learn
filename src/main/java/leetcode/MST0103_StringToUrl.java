package leetcode;


/**
 * @author : zhaochengming
 * @date : 2022/5/30 13:28
 * @description : https://leetcode.cn/problems/string-to-url-lcci/
 */
public class MST0103_StringToUrl {

    static class Solution {
        public String replaceSpaces(String S, int length) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < length; i ++) {
                if (S.charAt(i) == ' ') {
                    sb.append("%20");
                } else {
                    sb.append(S.charAt(i));
                }
            }

            return sb.toString();
        }
    }
}
