package leetcode;

import java.util.Deque;
import java.util.LinkedList;

/**
 * @author : zhaochengming
 * @date : 2023/6/16 14:59
 * @description : https://leetcode.cn/problems/reformat-phone-number/
 * 重新格式化电话号码
 */
public class L1694_ReformatPhoneNumber {
    static class Solution {
        public String reformatNumber(String number) {
            Deque<Character> q = new LinkedList<>();
            StringBuilder sb = new StringBuilder();
            for (char c: number.toCharArray()) {
                if (c == ' ' || c == '-') continue;
                q.offerLast(c);
                if (q.size() > 4) {
                    appendFromQueue(sb, q, 3);
                    sb.append('-');
                }
            }

            if (q.size() < 4) {
                appendFromQueue(sb, q, q.size());
            } else {
                appendFromQueue(sb, q, 2);
                sb.append("-");
                appendFromQueue(sb, q, 2);
            }

            return sb.toString();
        }

        private static void appendFromQueue(StringBuilder sb, Deque<Character> q, int num) {
            for (int i = 0; i < num; i ++) {
                sb.append(q.pollFirst());
            }
        }
    }
}
