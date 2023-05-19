package leetcode;

/**
 * @author : zhaochengming
 * @date : 2023/5/19 15:42
 * @description : https://leetcode.cn/problems/row-with-maximum-ones/
 * 1最多的行
 */
public class L2643_RowWithMaxOnes {
    static class Solution {
        public int[] rowAndMaximumOnes(int[][] mat) {
            int counts = 0, index = 0;

            for (int i = 0; i < mat.length; i ++) {
                int sum = 0;
                for (int x: mat[i]) {
                    sum += x;
                }

                if (sum > counts) {
                    counts = sum;
                    index = i;
                }
            }

            return new int[]{index, counts};
        }
    }
}
