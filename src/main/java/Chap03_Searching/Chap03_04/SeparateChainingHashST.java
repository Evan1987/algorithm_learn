package Chap03_Searching.Chap03_04;

import Chap03_Searching.Chap03_01.SequentialSearchST;
import Chap03_Searching.ST;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Evan
 * @date 2020/5/5 11:11
 */
public class SeparateChainingHashST<Key, Value> extends ST<Key, Value> {
    private static final int INIT_CAPACITY = 4;
    private int N;  // 键值对总数123
    private int M;  // 散列表大小
    private SequentialSearchST<Key, Value>[] st;

    public SeparateChainingHashST(){
        this(INIT_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public SeparateChainingHashST(int m){
        this.M = m;
        st = (SequentialSearchST<Key, Value>[]) new SequentialSearchST[this.M];
        for(int i = 0; i < this.M; i ++)
            st[i] = new SequentialSearchST<Key, Value>();
    }

    private void resize(int chains){
        SeparateChainingHashST<Key, Value> temp = new SeparateChainingHashST<>(chains);
        for(int i = 0; i < this.M; i ++){
            for(Key key: this.st[i].keys())
                temp.put(key, this.st[i].get(key));
        }
        this.M = temp.M;
        this.N = temp.N;
        this.st = temp.st;
    }

    private int hash(Key key){
        return (key.hashCode() & 0x7fffffff) % this.M;
    }

    @Override
    public void put(Key key, Value value) {
        this.nullKeyCheck(key);
        if(value == null){
            this.delete(key);
            return;
        }

        // double table size if average length of list >= 10
        if(this.N >= 10 * this.M) resize(2 * this.M);

        int i = this.hash(key);
        if(!st[i].contains(key)) this.N ++;
        st[i].put(key, value);
    }

    @Override
    public Value get(Key key) {
        this.nullKeyCheck(key);
        int i = this.hash(key);
        return this.st[i].get(key);
    }

    public void delete(Key key){
        this.nullKeyCheck(key);
        int i = this.hash(key);
        if(this.st[i].contains(key)) this.N --;
        this.st[i].delete(key);

        // halve table size if average length of list <= 2
        if(this.M >= INIT_CAPACITY && this.N <= 2 * this.M) this.resize(this.M / 2);
    }

    @Override
    public int size() {
        return this.N;
    }

    @Override
    public Iterable<Key> keys() {
        List<Key> queue = new ArrayList<>(this.N);
        for(int i = 0; i < this.M; i ++){
            for(Key key: this.st[i].keys())
                queue.add(key);
        }

        return queue;
    }
}
