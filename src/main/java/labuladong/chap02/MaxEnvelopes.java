package labuladong.chap02;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @author : zhaochengming
 * @date : 2022/4/20 14:05
 * @description : 信封嵌套（套娃）n * 2， 无序，最多嵌套多少个信封
 */
public class MaxEnvelopes {

    public static int solve(int[][] envelopes) {
        int n = envelopes.length;
        Arrays.sort(envelopes, (o1, o2) -> {
            // !! 宽度升序排列；宽度相同时，长度降序排列。以保证不会出现宽度相同的LIS
            return o1[0] == o2[0] ? o2[1] - o1[1] : o1[0] - o2[0];
        });

        int[] heights = new int[n];
        for (int i = 0; i < n; i ++) {
            heights[i] = envelopes[i][1];
        }

        return LIS.solve1(heights);
    }

    public static void main(String[] args) {
        int[][] envelopes = new int[4][2];
        envelopes[0] = new int[]{5, 4};
        envelopes[1] = new int[]{6, 4};
        envelopes[2] = new int[]{6, 7};
        envelopes[3] = new int[]{2, 3};

        System.out.println(solve(envelopes));
    }
}
