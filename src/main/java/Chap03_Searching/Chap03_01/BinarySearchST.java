package Chap03_Searching.Chap03_01;

import Chap03_Searching.OrderedST;
import Chap03_Searching.ST;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Evan
 * @date 2020/3/8 20:02
 * 基于有序数组的二分查找
 */
public class BinarySearchST<K extends Comparable<? super K>, V> extends OrderedST<K, V> {
    private static final int DEFAULT_CAPACITY = 2;
    private K[] keys;
    private V[] values;
    private int n = 0;

    public BinarySearchST(){
        this(DEFAULT_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public BinarySearchST(int capacity){
        this.keys = (K[]) new Comparable[capacity];
        this.values = (V[]) new Object[capacity];
    }

    @SuppressWarnings("unchecked")
    private void resize(int capacity){
        assert capacity >= this.n;
        K[] tempKeys = (K[]) new Comparable[capacity];
        V[] tempValues = (V[]) new Object[capacity];
        System.arraycopy(this.keys, 0, tempKeys, 0, this.n);
        System.arraycopy(this.values, 0, tempValues, 0, this.n);

        this.keys = tempKeys;
        this.values = tempValues;
    }

    @Override
    public void put(K key, V value) {
        this.nullKeyCheck(key);
        if(value == null){
            this.delete(key);
            return;
        }
        // the expect index to insert for key
        int i = this.rank(key);
        // key exists
        if(i < this.n && this.keys[i].compareTo(key) == 0){
            this.values[i] = value;
            return;
        }
        // key not exists, [i... (n-1)] -> move rightwards
        // double size if reach the capacity
        if(this.keys.length == this.n) this.resize(2 * this.n);

        if (n - i >= 0){
            System.arraycopy(this.keys, i, this.keys, i + 1, n - i);
            System.arraycopy(this.values, i, this.values, i + 1, n - i);
        }

        this.keys[i] = key;
        this.values[i] = value;
        this.n ++;
    }

    @Override
    public V get(K key) {
        this.nullKeyCheck(key);
        if(this.isEmpty()) return null;
        int i = this.rank(key);
        if(i < this.n && this.keys[i].compareTo(key) == 0) return this.values[i];
        return null;
    }

    /**
     * Removes the specified key and associated value from this symbol table
     * [(i+1)... (n-1)] -> move leftwards
     * */
    @Override
    public void delete(K key) {
        this.nullKeyCheck(key);
        if(this.isEmpty()) return;

        int i = this.rank(key);
        if(i == this.n || this.keys[i].compareTo(key) != 0) return;
        if (n - 1 - i >= 0) {
            System.arraycopy(this.keys, i + 1, this.keys, i, n - 1 - i);
            System.arraycopy(this.values, i + 1, this.values, i, n - 1- i);
        }
        this.n --;
        // to avoid loitering
        this.keys[this.n] = null;
        this.values[this.n] = null;
    }

    @Override
    public int size() {
        return this.n;
    }

    @Override
    public K min() {
        this.emptyCheck();
        return this.keys[0];
    }

    @Override
    public K max() {
        this.emptyCheck();
        return this.keys[this.n - 1];
    }

    @Override
    public K floor(K key) {
        this.nullKeyCheck(key);
        int i = this.rank(key);
        if(i < n && key.compareTo(keys[i]) == 0) return keys[i];
        if(i == 0) return null;
        return this.keys[i - 1];
    }

    @Override
    public K ceiling(K key) {
        this.nullKeyCheck(key);
        int i = this.rank(key);
        if(i == this.n) return null;
        return this.keys[i];
    }

    /**
     * Returns the number of keys in this symbol table strictly less than {@code key}.
     * @param  key the key
     * @return the number of keys in the symbol table strictly less than {@code key}
     * @throws IllegalArgumentException if {@code key} is {@code null}
     * */
    @Override
    public int rank(K key) {
        if(key == null) throw new IllegalArgumentException("the key is null");
        int lo = 0, hi = this.n - 1;
        while(lo <= hi){
            int mid = lo + (hi - lo) / 2;
            int cmp = key.compareTo(this.keys[mid]);
            if      (cmp < 0) hi = mid - 1;
            else if (cmp > 0) lo = mid + 1;
            else return mid;
        }
        return lo;
    }

    @Override
    public K select(int k) {
        if(k < 0 || k >= this.n) throw new IllegalArgumentException("the index is out of bound");
        return this.keys[k];
    }

    @Override
    public Iterable<K> keys(K lo, K hi) {
        this.nullKeyCheck(lo);
        this.nullKeyCheck(hi);

        List<K> keys = new LinkedList<>();
        for(int i = this.rank(lo); i <= this.rank(hi) && i < this.n; i ++)
            keys.add(this.keys[i]);
        return keys;
    }

    public static void main(String[] args) {
        ST.test(new BinarySearchST<>());
    }
}
