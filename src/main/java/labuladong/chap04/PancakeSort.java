package labuladong.chap04;

import java.util.LinkedList;
import java.util.List;

/**
 * @author : zhaochengming
 * @date : 2022/5/5 09:17
 * @description :
 */
public class PancakeSort {

    private static List<Integer> res = new LinkedList<>();

    // j 及其其上的煎饼翻转
    private static void reverse(int[] cake, int j) {
        int i = 0;
        while (i < j) {
            int temp = cake[i];
            cake[i] = cake[j];
            cake[j] = temp;
            i ++;
            j --;
        }
    }

    // 处理前n个，把最大的翻至最下面
    private static void sort(int[] cakes, int n) {
        if (n == 1) return;

        int maxCake = 0;
        int maxCakeIndex = 0;
        for (int i = 0; i < n; i ++) {
            if (cakes[i] > maxCake) {
                maxCake = cakes[i];
                maxCakeIndex = i;
            }
        }

        // 第一次翻转，将最大的由maxCakeIndex翻至最上面0，即(maxCakeIndex + 1)号其上的煎饼翻转
        reverse(cakes, maxCakeIndex);
        res.add(maxCakeIndex + 1);

        // 第二次翻转，将最大的由0翻至最下面n-1，即 n 号其上的煎饼全部翻转
        reverse(cakes, n - 1);
        res.add(n);

        // 递归调用
        sort(cakes, n - 1);
    }

    public static List<Integer> solve(int[] cakes) {
        sort(cakes, cakes.length);
        return res;
    }

    public static void main(String[] args) {
        int[] cakes = {3, 2, 4, 1};
        for (int x: solve(cakes)) {
            System.out.println(x);
        }

        for (int x: cakes) {
            System.out.print(x + " ");
        }
    }
}
