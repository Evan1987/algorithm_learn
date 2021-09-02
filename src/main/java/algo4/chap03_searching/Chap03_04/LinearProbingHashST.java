package algo4.chap03_searching.Chap03_04;


import algo4.chap03_searching.ST;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Evan
 * @date 2020/5/6 16:23
 */
public class LinearProbingHashST<Key, Value> extends ST<Key, Value> {
    // keep 12.5% - 50% full
    private static final int INIT_CAPACITY = 4;
    private int N;          // 键值对总数
    private int M;          // 散列表大小
    private Key[] keys;
    private Value[] values;

    public LinearProbingHashST(){
        this(INIT_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public LinearProbingHashST(int capacity){
        this.M = capacity;
        this.N = 0;
        this.keys = (Key[]) new Object[this.M];
        this.values = (Value[]) new Object[this.M];
    }

    private int hash(Key key){
        return (key.hashCode() & 0x7fffffff) % this.M;
    }

    private void resize(int capacity){
        LinearProbingHashST<Key, Value> temp = new LinearProbingHashST<>(capacity);
        for(int i = 0; i < this.M; i ++){
            if(this.keys[i] != null) temp.put(this.keys[i], this.values[i]);
        }

        this.keys = temp.keys;
        this.values = temp.values;
        this.M = temp.M;
    }

    @Override
    public void put(Key key, Value value) {
        this.nullKeyCheck(key);
        if(value == null){
            this.delete(key);
            return;
        }

        // double table size if 50% full
        if(this.N >= this.M / 2) this.resize(2 * this.M);

        int i;
        for(i = this.hash(key); keys[i] != null; i = (i + 1) % this.M){
            if(this.keys[i].equals(key)){
                this.values[i] = value;
                return;
            }
        }

        this.keys[i] = key;
        this.values[i] = value;
        this.N ++;
    }

    @Override
    public Value get(Key key) {
        this.nullKeyCheck(key);
        for(int i = hash(key); keys[i] != null; i = (i + 1) % this.M){
            if(this.keys[i].equals(key)) return this.values[i];
        }
        return null;
    }

    @Override
    public void delete(Key key){
        this.nullKeyCheck(key);
        if(!this.contains(key)) return;

        int i = hash(key);
        while(!key.equals(this.keys[i]))
            i = (i + 1) % this.M;

        this.keys[i] = null;
        this.values[i] = null;

        // re-hash all keys in same cluster
        i = (i + 1) % this.M;
        while(this.keys[i] != null){
            Key rehashKey = this.keys[i];
            Value rehashValue = this.values[i];
            this.keys[i] = null;
            this.values[i] = null;
            this.N --;
            this.put(rehashKey, rehashValue);
        }

        this.N --;
        // halves size of ST if it's less than 12.5%.
        if(this.N > 0 && this.N <= this.M / 8) this.resize(this.M / 2);

    }

    @Override
    public int size() {
        return this.N;
    }

    @Override
    public Iterable<Key> keys() {
        List<Key> queue = new ArrayList<>(this.N);
        for(Key key: this.keys){
            if(key != null) queue.add(key);
        }
        return queue;
    }
}
