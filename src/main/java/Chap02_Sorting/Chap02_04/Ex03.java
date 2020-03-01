package Chap02_Sorting.Chap02_04;

import Chap02_Sorting.PQ;

/**
 * @author Evan
 * @date 2020/3/1 19:14
 * Ex 2.4.3 使用多种数据结构实现优先队列
 */
public class Ex03 {
    public static void main(String[] args) {
        String[] arr = {"a", "b", "c", "f", "e", "d", "g"};
        int maxN = 10;
        UnorderedArrayMaxPQ<String> unorderedArrayMaxPQ = new UnorderedArrayMaxPQ<>(maxN);
        OrderedArrayMaxPQ<String> orderedArrayMaxPQ = new OrderedArrayMaxPQ<>(maxN);
        PQ.topMTest(unorderedArrayMaxPQ, arr, 5);
        PQ.topMTest(orderedArrayMaxPQ, arr, 5);
    }
}

class UnorderedArrayMaxPQ<Key extends Comparable<? super Key>> extends PQ<Key> {
    private Key[] pq;
    private int N = 0;

    @SuppressWarnings("unchecked")
    public UnorderedArrayMaxPQ(int maxN){
        this.pq = (Key[]) new Comparable[maxN];
    }

    @Override
    public void push(Key v) {
        this.pq[this.N ++] = v;
    }

    @Override
    public Key peek() {
        if(this.isEmpty()) return null;
        Key max = this.pq[0];
        for(int i = 1; i < this.N; i ++){
            if(greater(this.pq[i], max))
                max = this.pq[i];
        }
        return max;
    }

    @Override
    public Key pop() {
        int max = 0;
        for(int i = 1; i < this.N; i ++){
            if(less(this.pq[max], this.pq[i]))
                max = i;
        }

        this.exchange(max, this.N - 1);  // 将元素移至数组尾部
        Key res = this.pq[-- this.N];
        this.pq[this.N] = null;
        return res;
    }

    @Override
    public int size() {
        return this.N;
    }

    private void exchange(int i, int j){
        Key temp = this.pq[i];
        this.pq[i] = this.pq[j];
        this.pq[j] = temp;
    }
}


class OrderedArrayMaxPQ<Key extends Comparable<? super Key>> extends PQ<Key> {
    private Key[] pq;
    private int N = 0;

    @SuppressWarnings("unchecked")
    public OrderedArrayMaxPQ(int maxN){
        this.pq = (Key[])new Comparable[maxN];
    }

    /**
     * 比 v大的元素整体右移 1个单位
     * */
    @Override
    public void push(Key v) {
        int i = this.N - 1;

        // 从右侧遍历，遇见所有比 v大的元素都要右移
        while(i >= 0 && greater(this.pq[i], v)){
            this.pq[i + 1] = this.pq[i];
            i --;
        }

        this.pq[i + 1] = v;
        this.N ++;
    }

    @Override
    public Key peek() {
        return this.pq[this.N - 1];
    }

    @Override
    public Key pop() {
        Key max = this.pq[-- this.N];
        this.pq[this.N] = null;
        return max;
    }

    @Override
    public int size() {
        return this.N;
    }
}