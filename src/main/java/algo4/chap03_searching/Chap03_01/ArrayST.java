package algo4.chap03_searching.Chap03_01;

import algo4.chap03_searching.ST;

import java.util.LinkedList;

/**
 * @author Evan
 * @date 2020/3/9 15:02
 */
public class ArrayST<K, V> extends ST<K, V> {
    private static final int DEFAULT_CAPACITY = 2;
    private K[] keys;
    private V[] values;
    private int n = 0;

    public ArrayST(){
        this(DEFAULT_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public ArrayST(int capacity){
        this.keys = (K[])new Object[capacity];
        this.values = (V[]) new Object[capacity];
    }

    @SuppressWarnings("unchecked")
    private void resize(int capacity){
        assert capacity >= this.n;
        K[] tempKeys = (K[]) new Object[capacity];
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

        for(int i = 0; i < this.n; i ++){
            if(key.equals(this.keys[i])){
                this.values[i] = value;
                return;
            }
        }

        if(this.keys.length == this.n) this.resize(2 * this.keys.length);
        this.keys[this.n] = key;
        this.values[this.n] = value;
        this.n ++;
    }

    @Override
    public V get(K key) {
        this.nullKeyCheck(key);
        for(int i = 0; i < this.n; i ++){
            if(key.equals(this.keys[i]))
                return this.values[i];
        }
        return null;
    }

    @Override
    public int size() {
        return this.n;
    }

    @Override
    public void delete(K key) {
        this.nullKeyCheck(key);
        for(int i = 0; i < this.n; i ++){
            if(key.equals(this.keys[i])){
                this.keys[i] = this.keys[this.n - 1];
                this.values[i] = this.values[this.n - 1];
                this.keys[this.n - 1] = null;
                this.values[this.n - 1] = null;
                this.n --;
                return;
            }
        }
    }

    @Override
    public Iterable<K> keys() {
        ArrayST<K, V> out = this;
        return new LinkedList<K>(){{
           for(int i = 0; i < out.n; i ++)
               add(out.keys[i]);
        }};
    }

    public static void main(String[] args) {
        ST.test(new ArrayST<>());
    }
}
