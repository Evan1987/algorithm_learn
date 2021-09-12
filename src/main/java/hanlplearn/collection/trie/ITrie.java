package hanlplearn.collection.trie;

import java.util.Map;

public interface ITrie<V> {
    int build(Map<String, V> kv);
    V get(char[] key);
    V get(String key);
    V[] getValueArray(V[] a);
    boolean containsKey(String key);
    int size();
}
