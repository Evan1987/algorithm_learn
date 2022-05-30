package leetcode;

import java.util.*;

/**
 * @author : zhaochengming
 * @date : 2022/5/30 11:39
 * @description : https://leetcode.cn/problems/pascals-triangle-ii/
 */
public class L0119_PascalsTriangle2 {
    static class Solution {
        public List<Integer> getRow(int rowIndex) {
            List<Integer> ans = new ArrayList<>(rowIndex + 1);

            // C(n,m) = C(n, m - 1) * (n - m + 1) / m;
            ans.add(1);
            for (int i = 1; i <= rowIndex; i ++) {
                ans.add((int)((long) ans.get(i - 1) * (rowIndex - i + 1) / i));
            }

            return ans;

        }
    }
}
