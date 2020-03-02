package Chap02_Sorting.Chap02_04;

import javax.print.attribute.standard.Media;
import java.util.Comparator;

/**
 * @author Evan
 * @date 2020/3/2 14:22
 * 动态中位数查找，对数时间插入元素，常数时间找到中位数，对数时间删除中位数
 */
public class Ex30 {
    public static void main(String[] args) {
        test1();
        System.out.println();
        test2();
    }

    private static void test1(){
        MedianHolder<Integer> holder = new MedianHolder<>();
        Integer[] arr = {1, 3, 4, 2, 7, 10};
        for(Integer x: arr) {
            holder.add(x);
            System.out.println("Top " + holder.getCount() + " median: " + holder.getMid());
        }
    }

    private static void test2(){
        Integer[] arr = {1, 3, 4, 2};
        Integer[] append = {7, 10};
        MedianHolder<Integer> holder = new MedianHolder<>(arr);
        for(Integer x: append) {
            holder.add(x);
            System.out.println("Top " + holder.getCount() + " median: " + holder.getMid());
        }
    }
}

class MedianHolder <T extends Number> {
    private MinPQ<T> minPQ;
    private MinPQ<T> maxPQ;
    private int count = 0;
    private T mid;

    public MedianHolder(int capacity){
        assert capacity > 0;
        this.minPQ = new MinPQ<>(capacity, Comparator.comparingDouble(Number::doubleValue));
        this.maxPQ = new MinPQ<>(capacity, Comparator.comparingDouble(Number::doubleValue).reversed());
    }

    public MedianHolder(){
        this(10);
    }

    public MedianHolder(T[] keys){
        int n = keys.length;
        assert n > 0;
        this.minPQ = new MinPQ<>(n, Comparator.comparingDouble(Number::doubleValue));
        this.maxPQ = new MinPQ<>(n, Comparator.comparingDouble(Number::doubleValue).reversed());
        int k = n / 2;
        for(int i = 0; i < k; i ++)
            this.maxPQ.push(keys[i]);
        for(int i = k; i < k * 2; i ++)
            this.unbalancedInsert(keys[i]);
        k = k * 2;
        if(n - 1 == k)  // e.g. n is odd
            this.balancedInsert(keys[n - 1]);
        this.count = n;
    }

    public int getCount(){
        return this.count;
    }

    private boolean greater(T a, T b){
        return a.doubleValue() > b.doubleValue();
    }

    /**
     * Add element into holder keeping the minPQ and maxPQ balanced
     * */
    public void add(T x){
        if(this.mid == null) this.balancedInsert(x);
        else{
            this.maxPQ.push(this.mid);
            this.mid = null;
            this.unbalancedInsert(x);
        }
        this.count ++;
    }

    /**
     * Insert operation when maxPQ.size() = minPQ.size() + 1, mid = null
     * */
    private void unbalancedInsert(T x){
        T max = this.maxPQ.peek();
        if(!greater(max, x))    // max <= x
            this.minPQ.push(x);
        else{                   // max > x
            this.minPQ.push(this.maxPQ.pop());
            this.maxPQ.push(x);
        }
    }

    /**
     * Insert operation when maxPQ.size() = minPQ.size(), mid = null
     * */
    private void balancedInsert(T x){
        if(this.maxPQ.isEmpty() && this.minPQ.isEmpty())
            this.mid = x;
        else{
            T bottomMax = this.maxPQ.peek(), topMin = this.minPQ.peek();
            if(!greater(bottomMax, x) && !greater(x, topMin))  // bottomMax <= x <= topMin
                this.mid = x;
            else if(greater(x, topMin)){                       // bottomMax <= topMin < x
                this.mid = this.minPQ.pop();                   // mid = topMin
                this.minPQ.push(x);
            }
            else{                                              // x < bottomMax <= topMin
                this.mid = this.maxPQ.pop();                   // mid = bottomMax
                this.maxPQ.push(x);
            }
        }
    }

    public double getMid(){
        return this.mid == null ? (this.maxPQ.peek().doubleValue() + this.minPQ.peek().doubleValue()) / 2 : this.mid.doubleValue();
    }

    public double delMid(){
        double res = this.getMid();
        this.mid = null;
        return res;
    }
}
