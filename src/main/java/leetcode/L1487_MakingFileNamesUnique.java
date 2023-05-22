package leetcode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author : zhaochengming
 * @date : 2023/5/22 15:44
 * @description : https://leetcode.cn/problems/making-file-names-unique/
 * 保证文件名唯一
 */
public class L1487_MakingFileNamesUnique {
    static class Solution {
        public String[] getFolderNames(String[] names) {
            Map<String, Integer> seen = new HashMap<>();
            int n = names.length;
            String[] ans = new String[n];

            for (int i = 0; i < n; i ++) {
                String name = names[i];
                if (!seen.containsKey(name)) {
                    ans[i] = name;
                    seen.put(name, 1);
                } else {
                    int k = seen.get(name);
                    while (seen.containsKey(addSuffix(name, k))) {
                        k ++;
                    }
                    ans[i] = addSuffix(name, k);
                    seen.put(name, k + 1);
                    seen.put(ans[i], 1);
                }
            }
            return ans;
        }

        private String addSuffix(String name, int k) {
            return name + "(" + k + ")";
        }
    }
}
