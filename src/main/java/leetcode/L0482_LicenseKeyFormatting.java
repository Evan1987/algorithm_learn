package leetcode;

import java.util.*;

/**
 * @author : zhaochengming
 * @date : 2022/5/27 9:53
 * @description : https://leetcode.cn/problems/license-key-formatting/
 */
public class L0482_LicenseKeyFormatting {
    static class Solution {
        public String licenseKeyFormatting(String s, int k) {

            StringBuilder sb = new StringBuilder();
            int n = s.length();
            int count = 0;
            for (int i = n - 1; i >= 0; i --) {
                char c = s.charAt(i);
                if (c != '-') {
                    // 小写字母转大写
                    if (c >= 97) c -= 32;
                    sb.append(c);
                    count ++;
                    if (count % k == 0) {
                        sb.append('-');
                    }
                }
            }

            if (sb.length() > 0 && sb.charAt(sb.length() - 1) == '-') {
                sb.deleteCharAt(sb.length() - 1);
            }

            return sb.reverse().toString();
        }
    }
}
