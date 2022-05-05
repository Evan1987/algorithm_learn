package labuladong.chap05;

/**
 * @author : zhaochengming
 * @date : 2022/5/5 15:50
 * @description :
 */
public class ShapeWithinDays {

    private static int max(int[] weights) {
        int m = 0;
        for (int x: weights) {
            if (x > m) {
                m = x;
            }
        }
        return m;
    }

    private static int sum(int[] weights) {
        int m = 0;
        for (int x: weights) {
            m += x;
        }
        return m;
    }

    private static int getDays(int[] weights, int capacity) {
        int d = 1;
        int load = capacity;
        for (int x: weights) {
            if (load >= x) {
                load -= x;
            } else {
                load = capacity - x;
                d ++;
            }
        }

        return d;
    }

    public static int solve(int[] weights, int D) {
        int lo = max(weights);
        int hi = sum(weights) + 1;

        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            int d = getDays(weights, mid);
            if (d > D) {
                lo = mid + 1;
            } else {
                hi = mid;
            }
        }

        return lo;
    }

    public static void main(String[] args) {
        int[] weights = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int D = 5;
        System.out.println(solve(weights, D));
    }
}
