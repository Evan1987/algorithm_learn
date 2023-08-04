package leetcode;

import java.util.LinkedList;
import java.util.List;

/**
 * @author : zhaochengming
 * @date : 2023/8/3 17:07
 * @description : https://leetcode.cn/problems/remove-comments/
 * 删除注释
 */
public class L0722_RemoveComments {
    static class Solution {
        public List<String> removeComments(String[] source) {
            List<String> ans = new LinkedList<>();
            StringBuilder sb = new StringBuilder();

            boolean inBlock = false;
            for (String line: source) {
                int n = line.length();

                for (int i = 0; i < n; i ++) {
                    char c = line.charAt(i);
                    if (inBlock) {
                        // 在块注释里
                        // 寻找块注释结束位置
                        if (i < n - 1 && c == '*' && line.charAt(i + 1) == '/') {
                            inBlock = false;
                            i ++;
                        }
                    } else {
                        // 不在块注释里
                        if (i < n - 1 && c == '/' && line.charAt(i + 1) == '*') {
                            // 寻找块注释起始位置
                            inBlock = true;
                            i ++;
                        } else if (i < n - 1 && c == '/' && line.charAt(i + 1) == '/') {
                            // 寻找行注释位置
                            break;
                        } else {
                            sb.append(c);
                        }
                    }
                }

                if (!inBlock && sb.length() > 0) {
                    ans.add(sb.toString());
                    sb.setLength(0);  // 重置
                }
            }

            return ans;

        }
    }
}
