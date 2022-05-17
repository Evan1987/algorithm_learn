package leetcode;


/**
 * @author : zhaochengming
 * @date : 2022/5/17 0:05
 * @description : https://leetcode.cn/problems/number-of-students-unable-to-eat-lunch/
 */
public class L1700_NumberOfStudentsUnableToEatLunch {

    static class Solution {
        public int countStudents(int[] students, int[] sandwiches) {
            // 喜欢吃三明治的学生数量统计
            int[] count = new int[2];
            for (int student: students) {
                count[student] ++;
            }

            for (int sandwich: sandwiches) {
                if (count[sandwich] == 0) {
                    return count[1 - sandwich];
                }
                count[sandwich] --;
            }
            return 0;
        }
    }
}
