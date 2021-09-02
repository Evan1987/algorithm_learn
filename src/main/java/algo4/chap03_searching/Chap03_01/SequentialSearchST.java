package algo4.chap03_searching.Chap03_01;

import algo4.chap03_searching.ST;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Evan
 * @date 2020/3/8 17:31
 * 基于无序链表的顺序查找
 */
public class SequentialSearchST<K, V> extends ST<K, V> {

    private Node first;
    private int n;

    private class Node {
        K key;
        V value;
        Node next;
        Node(K key, V value, Node next){
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    @Override
    public void put(K key, V value) {
        if(key == null) throw new IllegalArgumentException("The key to put() is null");
        if(value == null){
            this.delete(key);
            return;
        }
        // If key already exists
        for(Node x = this.first; x != null; x = x.next){
            if(key.equals(x.key)){
                x.value = value;
                return;
            }
        }

        // If key not exists
        this.first = new Node(key, value, this.first);
        this.n ++;
    }

    @Override
    public V get(K key) {
        if(key == null) throw new IllegalArgumentException("Argument to get() is null");
        for(Node x = this.first; x != null; x = x.next){
            if(key.equals(x.key))
                return x.value;
        }
        return null;
    }

    @Override
    public int size() {
        return this.n;
    }

    @Override
    public void delete(K key) {
        if(key == null) return;

        // Match the first node
        if(key.equals(this.first.key)){
            this.first = this.first.next;
            this.n --;
            return;
        }

        // Not match the first node
        for(Node x = this.first; x.next != null; x = x.next){
            if(key.equals(x.next.key)){
                x.next = x.next.next;
                this.n --;
                return;
            }
        }
    }

    @Override
    public Iterable<K> keys() {
        List<K> keys = new LinkedList<>();
        for(Node x = this.first; x != null; x = x.next)
            keys.add(x.key);
        return keys;
    }

    public static void main(String[] args) {
        ST.test(new SequentialSearchST<String, Integer>());
    }
}
