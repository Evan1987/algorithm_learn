package labuladong.utils;

/**
 * @author : zhaochengming
 * @date : 2022/4/28 13:46
 * @description : 双链表节点
 */
public class DoubleLinkNode<K, V> {
    public K key;
    public V val;

    public DoubleLinkNode<K, V> prev;
    public DoubleLinkNode<K, V> next;

    public DoubleLinkNode(K key, V val) {
        this.key = key;
        this.val = val;
    }

    public DoubleLinkNode() {
        this(null, null);
    }

    public void addNext(DoubleLinkNode<K, V> node) {
        this.next = node;
        if (node != null) {
            node.prev = this;
        }
    }

    public void addPrev(DoubleLinkNode<K, V> node) {
        this.prev = node;
        if (node != null) {
            node.next = this;
        }
    }

}
