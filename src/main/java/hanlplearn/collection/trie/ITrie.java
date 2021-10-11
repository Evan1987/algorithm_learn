package hanlplearn.collection.trie;

import java.util.Map;

public interface ITrie<V> {
    void build(Map<String, V> kv);
    V get(char[] key);
    V get(String key);
    V[] getValueArray();
    boolean containsKey(String key);
    int size();
}
