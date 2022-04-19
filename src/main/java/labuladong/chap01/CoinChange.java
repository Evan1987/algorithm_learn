package labuladong.chap01;

import utils.annotations.WatchTime;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : zhaochengming
 * @date : 2022/4/18 19:52
 * @description : 凑零钱，返回最少硬币数量
 */
public class CoinChange {

    int[] coins;
    Map<Integer, Integer> memo;

    CoinChange(int[] coins) {
        this.coins = coins;
        this.memo = new HashMap<>();
    }

    private int dp(int amount) {
        if (amount < 0) return -1;
        if (amount == 0) return 0;
        int res = Integer.MAX_VALUE;
        for (int coin: this.coins) {
            int subProblem = dp(amount - coin);
            if (subProblem == -1) continue;
            res = Math.min(res, 1 + subProblem);
        }

        return res < Integer.MAX_VALUE ? res : -1;
    }

    private int dpWithMemo(int amount) {
        if (amount < 0) return -1;
        if (amount == 0) return 0;
        if (this.memo.containsKey(amount)) return this.memo.get(amount);
        int res = Integer.MAX_VALUE;
        for (int coin: this.coins) {
            int subProblem = dpWithMemo(amount - coin);
            if (subProblem == -1) continue;
            res = Math.min(res, 1 + subProblem);
        }

        res = res < Integer.MAX_VALUE ? res : -1;
        this.memo.put(amount, res);

        return res;
    }

    private int dpWithTable(int amount) {
        if (amount < 0) return -1;
        if (amount == 0) return 0;
        int MAX_VALUE = amount + 1;
        int[] table = new int[amount + 1];
        Arrays.fill(table, MAX_VALUE);

        table[0] = 0;
        for (int i = 0; i < table.length; i ++) {
            for (int coin: this.coins) {
                if (i < coin) continue;
                table[i] = Math.min(table[i], 1 + table[i - coin]);
            }
        }
        return table[amount] < MAX_VALUE ? table[amount] : -1;
    }

    @WatchTime
    public static int solve(int[] coins, int amount, String method) {
        CoinChange obj = new CoinChange(coins);
        if (method.equals("Brute Force")) {
            return obj.dp(amount);
        }

        if (method.equals("With Memo")) {
            return obj.dpWithMemo(amount);
        }

        if (method.equals("With Table")) {
            return obj.dpWithTable(amount);
        }

        return -1;
    }


    public static void main(String[] args) {
        int[] coins = new int[]{1, 2, 3, 4, 5};
        int amount = 21;
        System.out.println("Brute force: " + solve(coins, amount, "Brute Force"));
        System.out.println("With Memo: " + solve(coins, amount, "With Memo"));
        System.out.println("With Table: " + solve(coins, amount, "With Table"));
    }

}
