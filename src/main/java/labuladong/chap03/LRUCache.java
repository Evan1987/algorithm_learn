package labuladong.chap03;

import labuladong.utils.DoubleLinkList;
import labuladong.utils.DoubleLinkNode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : zhaochengming
 * @date : 2022/4/29 11:48
 * @description :
 */
public class LRUCache<K, V> {

    private final Map<K, DoubleLinkNode<K, V>> map;
    private final DoubleLinkList<K, V> cache;
    private final int capacity;  // 容量

    public LRUCache(int capacity) {
        this.map = new HashMap<>(capacity);
        this.cache = new DoubleLinkList<>();
        this.capacity = capacity;
    }

    // 将某个key提升为最近使用的, key一定存在
    private void makeRecently(K key) {
        DoubleLinkNode<K, V> node = this.map.get(key);
        this.cache.remove(node);
        this.cache.addLast(node);
    }

    // 添加最新元素
    private void addRecently(K key, V val) {
        DoubleLinkNode<K, V> node = new DoubleLinkNode<>(key, val);
        this.cache.addLast(node);
        this.map.put(key, node);
    }

    // 删除某个key
    private void deleteKey(K key) {
        DoubleLinkNode<K, V> node = this.map.remove(key);
        this.cache.remove(node);
    }

    // 删除最久未使用元素
    private void removeLeastRecently() {
        DoubleLinkNode<K, V> node = this.cache.removeFirst();
        this.map.remove(node.key);
    }

    // 调用
    public V get(K key) {

        DoubleLinkNode<K, V> node = this.map.get(key);
        if (node != null) {
            makeRecently(key);
            return node.val;
        }
        return null;
    }

    // 开启
    public void put(K key, V val) {

        // 如果已存在，需要重新update
        if (this.map.containsKey(key)) {
            deleteKey(key);
            addRecently(key, val);
            return;
        }

        // 不存在，需要新建，则需考虑是否超过容量
        if (this.capacity == this.cache.getSize()) {
            removeLeastRecently();
        }

        addRecently(key, val);
    }
}
