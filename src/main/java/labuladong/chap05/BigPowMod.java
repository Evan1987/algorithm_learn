package labuladong.chap05;

/**
 * @author : zhaochengming
 * @date : 2022/5/5 11:56
 * @description : 大数幂次求模 a^k % b
 */
public class BigPowMod {

    public static int solve(int a, int k, int b) {
        if (k == 0) return 1;
        a %= b;

        if (k % 2 == 1) {
            return (a * solve(a, k - 1, b)) % b;
        } else {
            int sub = solve(a, k / 2, b);
            return (sub * sub) % b;
        }
    }

    public static void main(String[] args) {
        System.out.println(solve(2, 12, 1337));
    }
}
