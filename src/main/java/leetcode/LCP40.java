package leetcode;

import java.util.*;

/**
 * @author : zhaochengming
 * @date : 2023/5/23 14:46
 * @description : https://leetcode.cn/problems/uOAnQW/
 * 心算挑战
 */
public class LCP40 {

    static class Solution {

        public int maxmiumScore(int[] cards, int cnt) {
            Arrays.sort(cards);
            int n = cards.length;

            // 前缀数组
            List<Integer> odds = new ArrayList<>(n);
            List<Integer> evens = new ArrayList<>(n);
            odds.add(0);
            evens.add(0);

            for (int i = n - 1; i >= 0; i --) {
                if (cards[i] % 2 == 0) {
                    evens.add(evens.get(evens.size() - 1) + cards[i]);
                } else {
                    odds.add(odds.get(odds.size() - 1) + cards[i]);
                }
            }

            int ans = 0;
            // i是奇数部分选取数量，i 是偶数
            // 0 <= cnt - i < evens.size()
            // 0 <= i < odds.size()
            for (int i = Math.max((((cnt - evens.size()) + 2) / 2 * 2), 0); i < Math.min(odds.size(), cnt + 1); i += 2) {
                ans = Math.max(ans, odds.get(i) + evens.get(cnt - i));
            }
            return ans;
        }
    }



    // 超时
    static class Solution1 {

        int MAX_VALUE = 0;

        public int maxmiumScore(int[] cards, int cnt) {
            Arrays.sort(cards);
            int n = cards.length;

            List<Integer> odds = new ArrayList<>(n);
            List<Integer> evens = new ArrayList<>(n);

            for (int i = n - 1; i >= 0; i --) {
                if (cards[i] % 2 == 0) {
                    evens.add(cards[i]);
                } else {
                    odds.add(cards[i]);
                }
            }

            dfs(odds, evens, 0, 0, 0, cnt);

            return MAX_VALUE;
        }

        private void dfs(List<Integer> odds, List<Integer> evens, int oddIndex, int evenIndex, int sum, int cnt) {
            if (cnt == 0) {
                this.MAX_VALUE = Math.max(this.MAX_VALUE, sum);
                return;
            }

            if (cnt % 2 == 1) {
                if (evenIndex == evens.size()) {
                    return;
                }
                sum += evens.get(evenIndex ++);
                cnt --;
                dfs(odds, evens, oddIndex, evenIndex, sum, cnt);
            } else {
                if (oddIndex <= odds.size() - 2) {
                    int s = sum;
                    int c = cnt;
                    int oi = oddIndex;
                    s += odds.get(oi ++);
                    s += odds.get(oi ++);
                    c -= 2;
                    dfs(odds, evens, oi, evenIndex, s, c);
                }

                if (evenIndex < evens.size()) {
                    sum += evens.get(evenIndex ++);
                    cnt --;
                    dfs(odds, evens, oddIndex, evenIndex, sum, cnt);
                }
            }
        }
    }

    public static void main(String[] args) {
        int[] cards = {10, 3};
        int cnt = 1;
        Solution sol = new Solution();
        System.out.println(sol.maxmiumScore(cards, cnt));
    }
}
