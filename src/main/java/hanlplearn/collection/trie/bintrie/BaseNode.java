package hanlplearn.collection.trie.bintrie;


import misc.ahocorasick.trie.Trie;
import org.jetbrains.annotations.NotNull;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.Map;


@SuppressWarnings("rawtypes")
public abstract class BaseNode<V> implements Comparable<BaseNode<V>> {

    // 状态数组
    static final Status[] ARRAY_STATUS =Status.values();

    // 子节点
    protected BaseNode[] children;

    // 节点状态
    protected Status status;

    // 节点代表的字符
    protected char c;

    // 节点存储的值
    protected V value;

    // 添加子节点
    protected abstract boolean addChild(BaseNode<V> child);

    // 获取子节点
    protected abstract BaseNode<V> getChild(char c);

    // 是否含有子节点
    public boolean hasChild(char c) {
        return getChild(c) != null;
    }

    // 获取字符
    public char getChar() {
        return this.c;
    }

    // 获取值
    public final V getValue() {
        return this.value;
    }

    // 设置值
    public final void setValue(V value) {
        this.value = value;
    }

    @Override
    public int compareTo(@NotNull BaseNode other) {
        return compareTo(other.getChar());
    }

    public int compareTo(char c) {
        return Character.compare(this.c, c);
    }

    public BaseNode<V> transition(String path, int begin) {
        BaseNode<V> cur = this;
        for (int i = begin; i < path.length(); i ++) {
            cur = cur.transition(path.charAt(i));
            if (cur == null) return null;
        }
        return cur;
    }

    public BaseNode<V> transition(char[] path, int begin) {
        BaseNode<V> cur = this;
        for (int i = begin; i < path.length; i ++) {
            cur = cur.transition(path[i]);
            if (cur == null) return null;
        }
        return cur;
    }

    // 转移状态
    public BaseNode<V> transition(char path) {
        return transition(this, path);
    }

    protected static <V> BaseNode<V> transition(BaseNode<V> node, char path) {
        BaseNode<V> cur = node.getChild(path);
        if (cur == null || cur.status == Status.UNDEFINED)
            return null;
        return cur;
    }

    // 获取节点的成词状态
    @SuppressWarnings("unchecked")
    protected void walk(StringBuilder sb, Collection<Map.Entry<String, V>> entries) {
        sb.append(this.c);
        if (this.status == Status.WORD_MIDDLE || this.status == Status.WORD_END) {
            entries.add(new TrieEntry(sb.toString(), this.value));
        }

        if (this.children == null) return;
        for (BaseNode<V> node: this.children) {
            if (node == null) continue;
            node.walk(new StringBuilder(sb), entries);
        }
    }

    public enum Status
    {
        // 未指定，用于删除词条
        UNDEFINED(0),

        // 不是词语的结尾
        NOT_WORD(1),

        // 是个词语的结尾，并且还可以继续
        WORD_MIDDLE(2),

        // 是个词语的结尾，并且没有继续
        WORD_END(3);

        private final int code;

        int getCode() {
            return this.code;
        }

        Status(int code) {
            this.code = code;
        }
    }

    public class TrieEntry extends AbstractMap.SimpleEntry<String, V> implements Comparable<TrieEntry> {

        public TrieEntry(String key, V value) {
            super(key, value);
        }

        @Override
        public int compareTo(@NotNull TrieEntry o) {
            return this.getKey().compareTo(o.getKey());
        }
    }



}
