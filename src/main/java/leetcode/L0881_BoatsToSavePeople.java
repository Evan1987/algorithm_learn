package leetcode;

import java.util.*;

/**
 * @author : zhaochengming
 * @date : 2022/5/23 13:04
 * @description : https://leetcode.cn/problems/boats-to-save-people/
 */
public class L0881_BoatsToSavePeople {
    static class Solution {
        public int numRescueBoats(int[] people, int limit) {
            int n = people.length;
            if (n == 1) return 1;
            Arrays.sort(people);

            int left = 0;
            int right = n - 1;
            int ans = 0;
            while (left <= right && people[right] > limit / 2) {
                if (people[left] + people[right] <= limit) {
                    left ++;
                }
                right --;
                ans ++;
            }

            // 剩下的人都可以2人一组乘船
            if (left < right) {
                return ans + (right - left) / 2 + 1;
            }

            return ans;

        }
    }
}
