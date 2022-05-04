package labuladong.chap04;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author : zhaochengming
 * @date : 2022/5/4 22:13
 * @description :
 */
public class NSum {

    public static class TwoSum {
        private final int[] nums;
        private final int start;

        public TwoSum(int[] sortedNums, int start) {
            this.nums = sortedNums;
            this.start = start;
        }

        public TwoSum(int[] sortedNums) {
            this(sortedNums, 0);
        }

        // 返回所有满足之和为target的不重复的组合
        public List<int[]> solve(int target) {
            List<int[]> res = new LinkedList<>();
            int lo = this.start, hi = this.nums.length - 1;
            while (lo < hi) {
                int left = this.nums[lo], right = this.nums[hi];
                int sum = left + right;
                if (sum < target) {
                    while (lo < hi && this.nums[lo] == left) lo ++;
                } else if (sum > target) {
                    while (lo < hi && this.nums[hi] == right) hi --;
                } else {
                    int[] r = {left, right};
                    res.add(r);
                    while (lo < hi && this.nums[lo] == left) lo ++;
                    while (lo < hi && this.nums[hi] == right) hi --;
                }
            }

            return res;
        }
    }

    public static class ThreeSum {
        private final int[] nums;

        public ThreeSum(int[] nums) {
            this.nums = nums;
            Arrays.sort(this.nums);
        }

        public List<int[]> solve(int target) {
            List<int[]> res = new LinkedList<>();
            int n = this.nums.length;
            for (int i = 0; i < n; ) {
                int left = this.nums[i];
                TwoSum obj = new TwoSum(this.nums, i + 1);
                for (int[] twoSumRes: obj.solve(target - left)) {
                    int[] r = new int[3];
                    r[0] = this.nums[i];
                    System.arraycopy(twoSumRes, 0, r, 1, 2);
                    res.add(r);
                }

                // 跳过头一个数字相同的情况
                do {
                    i ++;
                } while (i < n && this.nums[i] == left);
            }
            return res;
        }
    }

    // nums sorted first
    public static List<int[]> solve(int[] nums, int n, int start, int target) {
        int size = nums.length;
        List<int[]> res = new LinkedList<>();
        if (n < 2 || size < n) return res;
        if (n == 2) {
            TwoSum obj = new TwoSum(nums, start);
            return obj.solve(target);
        } else {
            for (int i = start; i < size; ) {
                int left = nums[i];
                for (int[] r: solve(nums, n - 1, i + 1, target - left)) {
                    int[] t = new int[n];
                    t[0] = left;
                    System.arraycopy(r, 0, t, 1, n - 1);
                    res.add(t);
                }

                do {
                    i ++;
                } while (i < n && nums[i] == left);
            }
        }
        return res;
    }

    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 3, 5, 7};
        int target = 6;
        TwoSum obj = new TwoSum(nums);
        for (int[] r: obj.solve(target)) {
            for (int x: r) {
                System.out.print(x + " ");
            }
            System.out.println();
        }

        ThreeSum obj2 = new ThreeSum(nums);
        for (int[] r: obj2.solve(target)) {
            for (int x: r) {
                System.out.print(x + " ");
            }
            System.out.println();
        }

        for (int[] r: solve(nums, 4, 0, 11)) {
            for (int x: r) {
                System.out.print(x + " ");
            }
            System.out.println();
        }
    }
}
