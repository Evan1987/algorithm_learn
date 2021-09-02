package algo4.chap02_sorting.Chap02_05;

/**
 * @author Evan
 * @date 2020/3/8 12:06
 * 稳定的优先队列
 */
public class StableMinPQ<Key extends Comparable<? super Key>> {
    private Key[] pq;
    private long[] time;
    private int n;
    private long timestamp = 1;

    @SuppressWarnings("unchecked")
    public StableMinPQ(int initCapacity){
        this.pq = (Key[]) new Comparable[initCapacity + 1];
        this.time = new long[initCapacity + 1];
        this.n = 0;
    }

    public StableMinPQ(){
        this(1);
    }

    public boolean isEmpty(){
        return this.n == 0;
    }

    @SuppressWarnings("unchecked")
    private void resize(int capacity){
        assert capacity > this.n;
        Key[] tempPQ = (Key[]) new Comparable[capacity];
        long[] tempTime = new long[capacity];
        System.arraycopy(this.pq, 0, tempPQ, 0, this.pq.length);
        System.arraycopy(this.time, 0, tempTime, 0, this.time.length);

        this.pq = tempPQ;
        this.time = tempTime;
    }

    public void push(Key key){
        if(this.n == this.pq.length - 1) this.resize(2 * this.pq.length);

        this.n ++;
        this.pq[this.n] = key;
        this.time[this.n] = ++ this.timestamp;
        this.swim(this.n);
        assert this.isMinHeap();
    }

    private void swim(int k){
        while (k > 1 && greater(k/2, k)){
            this.exchange(k/2, k);
            k = k/2;
        }
    }

    private void sink(int k){
        while(2 * k <= this.n){
            int j = 2 * k;
            if(j < n && greater(j, j + 1)) j ++;
            if(!greater(k, j)) break;
            exchange(k, j);
            k = j;
        }
    }

    private boolean greater(int i, int j){
        int cmp = this.pq[i].compareTo(this.pq[j]);
        if(cmp > 0) return true;
        if(cmp < 0) return false;
        return this.time[i] > this.time[j];
    }

    private void exchange(int i, int j){
        Key temp = this.pq[i];
        this.pq[i] = this.pq[j];
        this.pq[j] = temp;

        long tempTime = this.time[i];
        this.time[i] = this.time[j];
        this.time[j] = tempTime;
    }

    private boolean isMinHeap(){
        return isMinHeap(1);
    }

    private boolean isMinHeap(int k){
        if(k > n) return true;
        int left = 2 * k, right = left + 1;
        if(left <= n && greater(k, left)) return false;
        if(right <= n && greater(k, right)) return false;
        return isMinHeap(left) && isMinHeap(right);
    }
}
