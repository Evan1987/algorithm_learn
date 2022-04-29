package labuladong.utils;

/**
 * @author : zhaochengming
 * @date : 2022/4/28 13:49
 * @description : 双链表 只能从尾部插入，即尾部数据是最近使用，头部数据是最久未使用的
 */
public class DoubleLinkList<K, V> {
    private DoubleLinkNode<K, V> head;
    private DoubleLinkNode<K, V> tail;
    private int size;

    public DoubleLinkList () {
        this.head = new DoubleLinkNode<>(null, null);
        this.tail = new DoubleLinkNode<>(null, null);
        this.head.addNext(this.tail);
        this.size = 0;
    }

    // 尾部添加节点
    public void addLast(DoubleLinkNode<K, V> node) {
        node.addPrev(tail.prev);
        tail.addPrev(node);
        this.size ++;
    }

    // 删除节点，节点一定存在
    public void remove(DoubleLinkNode<K, V> node) {
        DoubleLinkNode<K, V> prev = node.prev;
        prev.addNext(node.next);
        this.size --;
    }

    // 删除头部节点，并返回该节点
    public DoubleLinkNode<K, V> removeFirst() {
        if (this.head.next == this.tail) {
            return null;
        }
        DoubleLinkNode<K, V> node = this.head.next;
        remove(node);
        return node;
    }

    public int getSize() {
        return this.size;
    }
}
