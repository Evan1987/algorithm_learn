package labuladong.chap05;

/**
 * @author : zhaochengming
 * @date : 2022/5/5 14:36
 * @description :
 */
public class KoKoEatingSpeed {

    private static int max(int[] piles) {
        int m = 0;
        for (int x: piles) {
            if (x > m) {
                m = x;
            }
        }
        return m;
    }

    private static int getHour(int[] piles, int speed) {
        int h = 0;
        for (int pile: piles) {
            h += pile / speed + (pile % speed == 0 ? 0 : 1);
        }
        return h;
    }

    public static int solve(int[] piles, int H) {
        if (H < piles.length) {
            return -1;
        }

        int lo = 1;
        int hi = max(piles) + 1;

        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            int h = getHour(piles, mid);

            if (h > H) {
                lo = mid + 1;
            } else {
                hi = mid;
            }
        }

        return lo;

    }

    public static void main(String[] args) {
        int[] piles = {1, 2, 3, 4, 5, 6};
        int H = 7;
        System.out.println(solve(piles, H));
    }
}
