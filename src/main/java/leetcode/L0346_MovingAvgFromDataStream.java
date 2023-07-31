package leetcode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author : zhaochengming
 * @date : 2023/4/26 17:45
 * @description : https://leetcode.cn/problems/moving-average-from-data-stream/
 * 数据流中的移动平均值
 */
public class L0346_MovingAvgFromDataStream {
    static class MovingAverage {
        Queue<Integer> q;
        int size;
        double sum;

        public MovingAverage(int size) {
            this.q = new LinkedList<>();
            this.size = size;
            this.sum = 0.0;
        }

        public double next(int val) {
            if (this.q.size() == this.size) {
                this.sum -= this.q.poll();
            }
            this.q.offer(val);
            this.sum += val;
            return this.sum / this.q.size();
        }

    }
}
