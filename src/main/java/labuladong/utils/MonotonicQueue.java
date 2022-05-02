package labuladong.utils;

import java.util.Deque;
import java.util.LinkedList;

/**
 * @author : zhaochengming
 * @date : 2022/5/3 0:41
 * @description : 单调队列
 */
public class MonotonicQueue<E extends Comparable<E>> {

    private final Deque<E> q = new LinkedList<>();

    // 删除队尾较小元素
    public void push(E e) {
        while (!this.q.isEmpty() && this.q.getLast().compareTo(e) <= 0) {
            q.pollLast();
        }
        q.addLast(e);
    }

    public E max() {
        if (this.q.isEmpty()) {
            return null;
        }
        return this.q.getFirst();
    }

    public void pop() {
        this.q.pollFirst();
    }

}
