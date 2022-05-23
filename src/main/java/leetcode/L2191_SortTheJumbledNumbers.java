package leetcode;


import java.util.*;

/**
 * @author : zhaochengming
 * @date : 2022/5/23 13:29
 * @description : https://leetcode.cn/problems/sort-the-jumbled-numbers/
 */
public class L2191_SortTheJumbledNumbers {
    static class Solution {
        public int[] sortJumbled(int[] mapping, int[] nums) {
            if (nums.length == 1) return nums;

            TreeMap<Status, Integer> m = new TreeMap<>();
            for (int i = 0; i < nums.length; i ++) {
                m.put(new Status(transform(mapping, nums[i]), i), nums[i]);
            }

            return m.values().stream().mapToInt(x -> x).toArray();
        }

        private int transform(int[] mapping, int target) {
            int res = 0;
            int base = 1;

            do {
                res += mapping[target % 10] * base;
                target /= 10;
                base *= 10;
            } while (target > 0);

            return res;
        }

        private static class Status implements Comparable<Status> {
            int key;
            int seq;

            Status(int key, int seq) {
                this.key = key;
                this.seq = seq;
            }

            @Override
            public int compareTo(Status that) {
                return this.key != that.key ? this.key - that.key : this.seq - that.seq;
            }
        }
    }
}
