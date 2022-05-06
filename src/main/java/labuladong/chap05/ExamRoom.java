package labuladong.chap05;

import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * @author : zhaochengming
 * @date : 2022/5/6 15:23
 * @description :
 */
public class ExamRoom {

    // 座位数
    private final int N;

    // 存放线段的有序集合
    private final TreeSet<Segment> pq;

    // 端点对应的线段
    private final Map<Integer, Segment> startMap;   // k 在左端点
    private final Map<Integer, Segment> endMap;     // k 在右端点


    public ExamRoom(int N) {
        this.N = N;
        this.pq = new TreeSet<>();
        this.startMap = new HashMap<>();
        this.endMap = new HashMap<>();
        // 添加虚拟节点
        addSegment(new Segment(-1, N));
    }

    // 给新来的考生安排座位
    public int seat() {
        // 取出最长线段
        Segment seg = this.pq.last();
        removeSegment(seg);

        // 两边没人坐两边
        if (seg.left == -1) {
            return 0;
        }

        if (seg.right == this.N) {
            return this.N - 1;
        }

        // 两边有人坐中间
        int seat = seg.mid();

        // 增加线段
        addSegment(new Segment(seg.left, seat));
        addSegment(new Segment(seat, seg.right));
        return seat;
    }

    // 坐在 p位置的考生离开
    public void leave(int p) {
        // 需要删除的线段
        Segment leftSeg = this.startMap.get(p);
        Segment rightSeg = this.endMap.get(p);
        removeSegment(leftSeg);
        removeSegment(rightSeg);

        // 新增合并线段
        Segment seg = new Segment(rightSeg.left, leftSeg.right);
        addSegment(seg);
    }

    private void addSegment(Segment seg) {
        this.pq.add(seg);
        this.startMap.put(seg.left, seg);
        this.endMap.put(seg.right, seg);
    }

    private void removeSegment(Segment seg) {
        this.pq.remove(seg);
        this.startMap.remove(seg.left);
        this.endMap.remove(seg.right);
    }


    private class Segment implements Comparable<Segment> {
        int left;
        int right;

        Segment(int left, int right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public int compareTo(@NotNull ExamRoom.Segment o) {
            // 距离大的优先
            int comp = this.midDistance() - o.midDistance();
            if (comp == 0) {
                // 索引小的优先
                return o.left - this.left;
            }
            return comp;
        }

        private int midDistance() {

            // 在两边的情况，端点距离计算
            if (this.left == -1) {
                return this.right;
            }

            if (this.right == N) {
                return this.right - this.left - 1;
            }

            return (this.right - this.left) / 2;
        }

        // 线段中点
        public int mid() {
            return this.left + (this.right - this.left) / 2;
        }
    }

}
