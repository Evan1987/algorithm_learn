package leetcode;

/**
 * @author : zhaochengming
 * @date : 2023/8/1 19:46
 * @description : https://leetcode.cn/problems/thousand-separator/
 */
public class L1556_ThousandSeparator {
    static class Solution {
        public String thousandSeparator(int n) {
            int count = 0;
            StringBuilder sb = new StringBuilder();

            do {
                sb.append(n % 10);
                n /= 10;
                count ++;
                if (count % 3 == 0 && n > 0) {
                    sb.append(".");
                }
            } while (n > 0);

            sb.reverse();
            return sb.toString();
        }
    }
}
