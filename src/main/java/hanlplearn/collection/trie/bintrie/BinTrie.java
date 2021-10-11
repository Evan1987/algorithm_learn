package hanlplearn.collection.trie.bintrie;


import hanlplearn.collection.trie.ITrie;
import java.util.*;

/**
 * 首字直接分配内存，之后二分动态数组的Trie树，能够平衡时间和空间
 */
public class BinTrie<V> extends BaseNode<V> implements ITrie<V> {

    private int size;

    public BinTrie(){
        this.children = new BaseNode[65536];    // (int)Character.MAX_VALUE
        this.status = Status.NOT_WORD;
        this.size = 0;
    }

    public BinTrie(Map<String, V> m) {
        this();
        build(m);
    }

    // 插入k, v
    public void put(char[] key, V value) {
        BaseNode<V> branch = this;
        // 遍历至最后一个字符
        for (int i = 0; i < key.length - 1; i ++) {
            branch.addChild(new Node<>(key[i], Status.NOT_WORD, null));
            branch = branch.getChild(key[i]);
        }
        // 最后一个字加入时属性为 end
        if (branch.addChild(new Node<>(key[key.length - 1], Status.WORD_END, value))) {
            this.size ++;
        }
    }

    public void put(String key, V value) {
        if (key == null || key.length() == 0) return;
        put(key.toCharArray(), value);
    }

    @Override
    public void build(Map<String, V> kv) {
        if (kv != null) {
            for (Map.Entry<String, V> e: kv.entrySet()) {
                put(e.getKey(), e.getValue());
            }
        }
    }

    public void remove(char[] key) {
        BaseNode<V> branch = this;
        for (int i = 0; i < key.length - 1; i ++) {
            branch = branch.getChild(key[i]);
            if (branch == null) return;
        }
        // 最后一个字设为undefined
        if (branch.addChild(new Node<>(key[key.length - 1], Status.UNDEFINED, null))) {
            this.size --;
        }
    }

    public void remove(String key) {
        if (key == null || key.length() == 0) return;
        remove(key.toCharArray());
    }

    @Override
    public V get(char[] key) {
        BaseNode<V> branch = this;
        for(char c: key) {
            branch = branch.getChild(c);
            if (branch == null) return null;
        }

        // 保证只有成词的节点被返回
        if (branch.status == Status.WORD_END || branch.status == Status.WORD_MIDDLE)
            return branch.getValue();
        return null;
    }

    @Override
    public V get(String key) {
        if (key == null || key.length() == 0) return null;
        return get(key.toCharArray());
    }

    // 获取键值对集合
    @SuppressWarnings("unchecked")
    public Collection<Map.Entry<String, V>> entrySet() {
        List<Map.Entry<String, V>> entries = new LinkedList<>();
        StringBuilder sb = new StringBuilder();
        for (BaseNode<V> node: this.children) {
            if (node == null) continue;
            node.walk(sb, entries);
        }
        return entries;
    }

    @SuppressWarnings("unchecked")
    @Override
    public V[] getValueArray() {
        V[] res = (V[]) new Object[this.size];
        int i = 0;
        for (Map.Entry<String, V> e: entrySet()) {
            res[i ++] = e.getValue();
        }
        return res;
    }

    public Set<String> keySet() {
        TreeSet<String> keys = new TreeSet<>();
        for (Map.Entry<String, V> e: entrySet()) {
            keys.add(e.getKey());
        }
        return keys;
    }

    @Override
    public boolean containsKey(String key) {
        return get(key) != null;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    protected boolean addChild(BaseNode<V> node) {
        boolean add = false;
        char c = node.getChar();
        BaseNode<V> target = getChild(c);
        if (target == null) {
            this.children[c] = node;
            add = true;
        } else {
            switch (node.status) {
                case UNDEFINED:
                    if (target.status != Status.NOT_WORD) {
                        target.status = Status.NOT_WORD;
                        add = true;
                    }
                    break;
                case NOT_WORD:
                    if (target.status == Status.WORD_END) {
                        target.status = Status.WORD_MIDDLE;
                    }
                    break;
                case WORD_END:
                    if (target.status == Status.NOT_WORD) {
                        target.status = Status.WORD_MIDDLE;
                    }
                    if (target.getValue() == null) {
                        add = true;
                    }
                    target.setValue(node.getValue());
                    break;
            }
        }
        return add;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected BaseNode<V> getChild(char c) {
        return this.children[c];
    }
}
