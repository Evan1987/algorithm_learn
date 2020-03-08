package Chap03_Searching;

import java.util.NoSuchElementException;

/**
 * @author Evan
 * @date 2020/3/8 13:55
 * 有序符号表
 */
public abstract class OrderedST<K extends Comparable<? super K>, V> extends ST<K, V> {
    public abstract K min();  // the min key
    public abstract K max();  // the max key
    public abstract K floor(K key);  // the max key which <= given key
    public abstract K ceiling(K key);  // the min key which >= given key
    public abstract int rank(K key);  // the num of keys which < key
    public abstract K select(int k);  // select the key which rank k
    public abstract Iterable<K> keys(K lo, K hi);  // return the keys between [lo...hi]

    // delete the minimum key
    public void deleteMin(){
        this.emptyCheck();
        this.delete(this.min());
    }

    // delete the maximum key
    public void deleteMax(){
        this.emptyCheck();
        this.delete(this.max());
    }

    // the num of keys between [lo...hi]
    public int size(K lo, K hi){
        this.nullKeyCheck(lo);
        this.nullKeyCheck(hi);
        if(hi.compareTo(lo) < 0) return 0;
        int n = this.rank(hi) - this.rank(lo);
        if(this.contains(hi)) return  n + 1;
        return n;
    }

    @Override
    public Iterable<K> keys(){
        return this.keys(this.min(), this.max());
    }


}
