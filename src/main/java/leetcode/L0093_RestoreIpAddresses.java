package leetcode;

import java.util.*;

/**
 * @author : zhaochengming
 * @date : 2025/12/13 21:21
 * @description : https://leetcode.cn/problems/restore-ip-addresses/
 * 复原ip地址
 */
public class L0093_RestoreIpAddresses {
    static class Solution {
        private final List<String> ans = new LinkedList<>();
        private final int[] ip = new int[4];

        public List<String> restoreIpAddresses(String s) {
            dfs(s, 0, 0);
            return ans;
        }

        private String generateIp(int[] ip) {
            StringBuilder builder = new StringBuilder();
            for (int sec: ip) {
                builder.append('.');
                builder.append(sec);
            }
            return builder.substring(1);
        }

        private void dfs(String s, int start, int sectionId) {
            int resLength = s.length() - start;
            int resCount = 4 - sectionId;
            if (resLength < resCount || resLength > 3 * resCount) return;

            if (sectionId == 4) {
                if (start == s.length()) {
                    ans.add(generateIp(ip));
                }
                return;
            }

            if (s.charAt(start) == '0') {
                ip[sectionId] = 0;
                dfs(s, start + 1, sectionId + 1);
                return;
            }
            int addr = 0;
            for (int end = start; end < s.length(); end ++) {
                addr = addr * 10 + s.charAt(end) - '0';
                if (0 < addr && addr <= 0xff) {
                    ip[sectionId] = addr;
                    dfs(s, end + 1, sectionId + 1);
                } else {
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
        String s = "25525511135";
        List<String> ans = new Solution().restoreIpAddresses(s);
        for (String ip: ans) {
            System.out.println(ip);
        }
    }
}
