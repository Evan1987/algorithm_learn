package labuladong.chap05;

import java.util.Arrays;

/**
 * @author : zhaochengming
 * @date : 2022/5/5 11:46
 * @description : [2, n) 之间的素数
 */
public class CountPrimes {

    public static int solve(int n) {
        boolean[] isPrime = new boolean[n];
        Arrays.fill(isPrime, true);

        for (int i = 2; i * i < n; i ++) {
            if (isPrime[i]) {
                // 倍数置false，且起始为i*i防止冗余（ 2*3 和 3*2 ）
                for (int j = i * i; j < n; j += i) {
                    isPrime[j] = false;
                }
            }
        }

        int count = 0;
        for (int i = 2;  i < n; i ++) {
            if (isPrime[i]) count ++;
        }

        return count;

    }
}
