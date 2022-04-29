package labuladong.chap03;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

/**
 * @author : zhaochengming
 * @date : 2022/4/29 13:44
 * @description :
 */
public class LFUCache<K, V> {

    private final Map<K, V> keyToVal;
    private final Map<K, Integer> keyToFreq;
    private final Map<Integer, LinkedHashSet<K>> freqToKeys;
    private int minFreq;
    private final int capacity;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.minFreq = 0;
        this.keyToVal = new HashMap<>();
        this.keyToFreq = new HashMap<>();
        this.freqToKeys = new HashMap<>();
    }

    public int getSize() {
        return this.keyToVal.size();
    }

    private void removeMinFreqKey() {
        LinkedHashSet<K> keys = this.freqToKeys.get(this.minFreq);
        K deletedKey = keys.iterator().next();
        // keys 可能为空，minFreq可能会变动，但是没必要此时修改。因为该方法触发的时机是加入新key，新key的freq肯定是最小的1
        keys.remove(deletedKey);

        this.keyToFreq.remove(deletedKey);
        this.keyToVal.remove(deletedKey);
    }

    // key一定存在
    private void increaseFreq(K key) {
        int freq = this.keyToFreq.get(key);
        LinkedHashSet<K> keys = this.freqToKeys.get(freq);
        keys.remove(key);

        // 更新minFreq
        if (keys.isEmpty()) {
            this.freqToKeys.remove(freq);
            if (freq == this.minFreq) {
                this.minFreq ++;
            }
        }

        // 更新记录
        freq ++;
        this.keyToFreq.put(key, freq);
        this.freqToKeys.putIfAbsent(freq, new LinkedHashSet<>());
        this.freqToKeys.get(freq).add(key);

    }

    // 调用
    public V get(K key) {
        V val = this.keyToVal.get(key);
        if (val == null) {
            return null;
        }

        increaseFreq(key);
        return this.keyToVal.get(key);
    }

    // 新增
    public void put(K key, V val) {
        if (this.capacity <= 0) {
            return;
        }

        // key 已存在
        if (this.keyToVal.containsKey(key)) {
            this.keyToVal.put(key, val);
            increaseFreq(key);
            return;
        }

        // 新增key
        if (this.capacity == getSize()) {
            removeMinFreqKey();
        }

        this.keyToVal.put(key, val);
        this.keyToFreq.put(key, 1);
        this.freqToKeys.putIfAbsent(1, new LinkedHashSet<>());
        this.freqToKeys.get(1).add(key);
        this.minFreq = 1;
    }

}
