package leetcode;

/**
 * @author : zhaochengming
 * @date : 2023/5/19 16:30
 * @description : https://leetcode.cn/problems/duplicate-zeros/
 * 复写零， 原地修改，不超过数组长度
 */
public class L1089_DuplicateZero {
    static class Solution {
        public void duplicateZeros(int[] arr) {
            int n = arr.length, i = 0, j = 0;
            while (j < n) {
                if (arr[i] == 0) j ++;
                i ++;
                j ++;
            }

            i --;
            j --;

            // 从后往前赋值
            while (i >= 0) {
                if (j < n) arr[j] = arr[i];
                if (arr[i] == 0 && -- j >= 0) {
                    arr[j] = 0;
                }
                i --;
                j --;
            }

        }
    }

    public static void main(String[] args) {
        int[] arr = {1, 0, 2, 3, 0, 4, 5, 0};
        Solution sol = new Solution();
        sol.duplicateZeros(arr);
        for (int x: arr) {
            System.out.print(x + " ");
        }
    }
}
