package Chap05_String.Chap05_02;

/**
 * @author zhaochengming
 * @date 2020/9/29 19:51
 */
public class TST<Value> extends AbstractStringST<Value> {

    private int n;                              // the size
    private Node<Value> root;                   // the root of TST

    private static class Node<Value> {
        private char c;                         // the character of node
        private Node<Value> left, mid, right;   // left, middle, right sub tree
        private Value val;                      // value associated with char
    }

    public TST() {
        this.n = 0;
    }

    @Override
    public void put(String key, Value val) {
        if(val == null) this.delete(key);
        this.root = this.put(this.root, key, val, 0);
    }

    private Node<Value> put(Node<Value> x, String key, Value val, int d) {
        char c = key.charAt(d);
        if(x == null) {
            x = new Node<>();
            x.c = c;
        }

        if(c < x.c)                     x.left = put(x.left, key, val, d);
        else if(c > x.c)                x.right = put(x.right, key, val, d);
        else if(d < key.length() - 1)   x.mid = put(x.mid, key, val, d + 1);
        else {
            if(x.val == null) this.n ++;        // leaf node x just initialized(newly created)
            x.val = val;
        }
        return x;
    }

    @Override
    public Value get(String key) {
        if(key.length() == 0) throw new IllegalArgumentException("key must not be empty string");
        Node<Value> x = this.get(this.root, key, 0);
        if(x == null) return null;
        return x.val;
    }

    // return the sub-trie corresponding to the given key
    private Node<Value> get(Node<Value> x, String key, int d) {
        if(x == null) return null;
        char c = key.charAt(d);
        if(c < x.c)                 return get(x.left, key, d);
        if(c > x.c)                 return get(x.right, key, d);
        if(d < key.length() - 1)    return get(x.mid, key, d + 1);
        return x;
    }

    @Override
    public void delete(String key) {

    }

    @Override
    public String longestPrefixOf(String s) {
        return null;
    }

    @Override
    public Iterable<String> keysWithPrefix(String s) {
        return null;
    }

    @Override
    public Iterable<String> keysThatMatch(String s) {
        return null;
    }

    @Override
    public int size() {
        return this.n;
    }
}
