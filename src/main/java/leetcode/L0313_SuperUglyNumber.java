package leetcode;

import java.util.*;

/**
 * @author : zhaochengming
 * @date : 2023/6/9 10:47
 * @description : https://leetcode.cn/problems/super-ugly-number/
 * 超级丑数
 */
public class L0313_SuperUglyNumber {
    // 利用pq
    static class Solution {

        static class Info {
            int prime;
            int mulIndex;
            long score;

            Info(int prime, int mulIndex, long score) {
                this.prime = prime;
                this.mulIndex = mulIndex;
                this.score = score;
            }
        }

        public int nthSuperUglyNumber(int n, int[] primes) {
            if (n == 1) return 1;

            long[] res = new long[n];
            res[0] = 1;
            PriorityQueue<Info> pq = new PriorityQueue<>(Comparator.comparingLong(o -> o.score));
            for (int prime: primes) {
                pq.offer(new Info(prime, 0, prime));
            }

            int next = 1;
            while (next < n) {
                Info info = pq.poll();
                long score = info.score;
                res[next ++] = score;
                info.score = info.prime * res[++ info.mulIndex];
                pq.offer(info);
                while (!pq.isEmpty() && pq.peek().score == score) {
                    Info other = pq.poll();
                    other.score = other.prime * res[++ other.mulIndex];
                    pq.offer(other);
                }
            }

            return (int) res[n - 1];
        }
    }
}
