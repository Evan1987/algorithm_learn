package Chap02_Sorting.Chap02_04;

import Chap02_Sorting.PQ;

/**
 * @author Evan
 * @date 2020/3/1 10:10
 */
public class MinPQ <Key extends Comparable<? super Key>> extends PQ<Key> {
    private Key[] keys;       // 堆数组
    int N;            // pq[0]不使用

    @SuppressWarnings("unchecked")
    public MinPQ(int n){
        if(n < 0) throw new IllegalArgumentException("The max capacity must be greater than 0");
        this.keys = (Key[]) new Comparable[n + 1];
        this.N = 0;
    }

    @Override
    public void push(Key v) {
        this.keys[++ this.N] = v;  // 直接插入底部
        this.swim(this.N);       // 上浮
    }

    @Override
    public Key peek() {
        return this.keys[1];
    }

    @Override
    public Key pop() {
        Key max = this.keys[1];
        exchange(1, this.N --);     // 将底部元素交换至根
        this.keys[this.N + 1] = null;   // 防止对象游离
        this.sink(1);              // 下沉恢复有序性
        return max;
    }

    @Override
    public int size() {
        return this.N;
    }

    private boolean less(int i, int j){
        return less(this.keys[i], this.keys[j]);
    }

    private void exchange(int i, int j){
        Key t = this.keys[i];
        this.keys[i] = this.keys[j];
        this.keys[j] = t;
    }

    /**
     * 元素上浮有序化
     * */
    void swim(int k){
        while(k > 1 && less(k, k / 2)){  // 更改less顺序
            exchange(k / 2, k);
            k /= 2;
        }
    }

    /**
     * 元素下沉有序化
     * */
    void sink(int k){
        while(2 * k <= this.N){
            int j = 2 * k;
            if(j < this.N && less(j + 1, j)) j ++;  // 下层的最小值
            if(!less(j, k)) break;                    // 如果不小于下层最小值，则无法再下沉
            exchange(k, j);
            k = j;
        }
    }

    public static void main(String[] args) {
        String[] arr = {"a", "b", "c", "f", "e", "d", "g"};
        MinPQ<String> minPQ = new MinPQ<>(10);
        int M = 5;
        topMTest(minPQ, arr, M);  // c,d,e,f,g 小顶堆pop会把最小的pop掉，最后只留top M个，由小到大排列
    }
}
