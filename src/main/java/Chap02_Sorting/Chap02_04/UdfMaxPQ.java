package Chap02_Sorting.Chap02_04;

import Chap02_Sorting.PQ;

/**
 * @author Evan
 * @date 2020/2/29 19:36
 * top-up PQ based on Heap
 */
public class UdfMaxPQ<Key extends Comparable<? super Key>> extends PQ<Key> {

    private Key[] pq;       // 堆数组
    private int N = 0;      // pq[0]不使用

    @SuppressWarnings("unchecked")
    public UdfMaxPQ(int n){
        this.pq = (Key[]) new Comparable[n];
    }

    @Override
    public void push(Key v) {
        this.pq[++ this.N] = v;  // 直接插入底部
        this.swim(this.N);       // 上浮
    }

    @Override
    public Key peek() {
        return this.pq[1];
    }

    @Override
    public Key pop() {
        Key max = this.pq[1];
        exchange(1, this.N --);     // 将底部元素交换至根
        this.pq[this.N + 1] = null;   // 防止对象游离
        this.sink(1);              // 下沉恢复有序性
        return max;
    }

    @Override
    public int size() {
        return this.N;
    }

    private boolean less(int i, int j){
        return less(this.pq[i], this.pq[j]);
    }

    private void exchange(int i, int j){
        Key t = this.pq[i];
        this.pq[i] = this.pq[j];
        this.pq[j] = t;
    }

    /**
     * 元素上浮有序化
     * */
    private void swim(int k){
        while(k > 1 && less(k / 2, k)){
            exchange(k / 2, k);
            k /= 2;
        }
    }

    /**
     * 元素下沉有序化
     * */
    private void sink(int k){
        while(2 * k <= this.N){
            int j = 2 * k;
            if(j < this.N && less(j, j + 1)) j ++;  // 下层的最大值
            if(!less(k, j)) break;                    // 如果不小于下层最大值，则无法再下沉
            exchange(k, j);
            k = j;
        }
    }

    public static void main(String[] args) {
        String[] arr = {"a", "b", "c", "f", "e", "d", "g"};
        UdfMaxPQ<String> udfMaxPQ = new UdfMaxPQ<>(10);
        int M = 5;
        topMTest(udfMaxPQ, arr, M);  // e,d,c,b,a 大顶堆pop会把最大的pop掉，最后只留bottom M个，由大到小排列
    }
}
