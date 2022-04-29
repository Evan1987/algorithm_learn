package labuladong.chap03;

import java.util.LinkedHashMap;

/**
 * @author : zhaochengming
 * @date : 2022/4/29 13:29
 * @description : 基于java 自带数据结构 LinkedHashMap来实现
 */
public class LRUCache2<K, V> {
    private final LinkedHashMap<K, V> cache;
    private final int capacity;

    public LRUCache2(int capacity) {
        this.capacity = capacity;
        this.cache = new LinkedHashMap<>();
    }

    private void makeRecently(K key) {
        V val = this.cache.get(key);
        cache.remove(key);
        cache.put(key, val);
    }

    public V get(K key) {
        V val = this.cache.get(key);
        if (val != null) {
            makeRecently(key);
        }
        return val;
    }

    public void put(K key, V val) {
        if (this.cache.containsKey(key)) {
            this.cache.put(key, val);
            makeRecently(key);
        }

        if (this.capacity == this.cache.size()) {
            K oldestKey = this.cache.keySet().iterator().next();
            this.cache.remove(oldestKey);
        }

        this.cache.put(key, val);
    }

}
