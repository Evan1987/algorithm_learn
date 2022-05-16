package leetcode;

/**
 * @author : zhaochengming
 * @date : 2022/5/16 19:52
 * @description : https://leetcode.cn/problems/excel-sheet-column-number/
 */
public class L0171_ExcelSheetColumnNumber {
    static class Solution {
        public int titleToNumber(String columnTitle) {
            int ans = 0;
            int n = columnTitle.length();
            int d = 1;
            for (int i = n - 1; i >= 0; i --) {
                ans += d * (columnTitle.charAt(i) - 'A' + 1);
                d *= 26;
            }

            return ans;
        }
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        System.out.println(sol.titleToNumber("AB"));
    }
}
