package leetcode;

import java.util.Iterator;

/**
 * @author : zhaochengming
 * @date : 2023/6/15 19:59
 * @description : https://leetcode.cn/problems/flatten-2d-vector/
 * 展开2维向量
 */
public class L0251_Flatten2DVector {
    static class Vector2D implements Iterator<Integer> {

        int[][] vec;
        int i = 0;
        int j = 0;

        public Vector2D(int[][] vec) {
            this.vec = vec;
        }

        @Override
        public boolean hasNext() {
            return i < vec.length;
        }

        @Override
        public Integer next() {
            while (i < vec.length && vec[i].length == 0) {
                i ++;
            }

            int v = vec[i][j];
            if (j == vec[i].length - 1) {
                i ++;
                j = 0;
            } else {
                j ++;
            }

            return v;
        }
    }
}
