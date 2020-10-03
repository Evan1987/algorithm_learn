package Chap05_String.Chap05_02;

import java.util.*;

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
        this.root = this.delete(this.root, key, 0);
    }

    private Node<Value> delete(Node<Value> x, String key, int d) {
        if(x == null) return null;
        char c = key.charAt(d);
        if(c < x.c)                     x.left = delete(x.left, key, d);
        else if(c > x.c)                x.right = delete(x.right, key, d);
        else if(d < key.length() - 1)   x.mid = delete(x.mid, key, d + 1);
        else {
            if(x.val != null) this.n --;
            x.val = null;
        }

        if(x.val == null && x.left == null && x.mid == null && x.right == null) return null;
        return x;
    }

    @Override
    public String longestPrefixOf(String s) {
        if(s.length() == 0) return null;
        int length = this.longestPrefixOf(this.root, s, 0, -1);
        if(length == -1) return null;
        return s.substring(0, length + 1);
    }

    public String longestPrefixOfV2(String s) {
        if(s.length() == 0) return null;
        int length = 0, i = 0;
        Node<Value> x = this.root;

        while(x != null && i < s.length()) {
            char c = s.charAt(i);
            if(c < x.c)             x = x.left;
            else if(c > x.c)        x = x.right;
            else {
                i ++;
                if(x.val != null) length = i;
                x = x.mid;
            }
        }

        return s.substring(0, length);

    }

    private int longestPrefixOf(Node<Value> x, String key, int d, int length) {
        if(x == null) return length;
        char c = key.charAt(d);
        if(c < x.c)                 return this.longestPrefixOf(x.left, key, d, length);
        if(c > x.c)                 return this.longestPrefixOf(x.right, key, d, length);
        if(x.val != null)           length = d;
        if(d < key.length() - 1)    return this.longestPrefixOf(x.mid, key, d + 1, length);
        return key.length();
    }

    private void collect(Node<Value> x, StringBuilder prefix, List<String> queue) {
        if(x == null) return;
        collect(x.left, prefix, queue);
        collect(x.right, prefix, queue);

        prefix.append(x.c);
        if(x.val != null) queue.add(prefix.toString());
        collect(x.mid, prefix, queue);
        prefix.deleteCharAt(prefix.length() - 1);  // very important, keep unchanged
    }

    private void collect(Node<Value> x, StringBuilder prefix, int d, String pattern, List<String> queue) {
        if(x == null) return;
        char c = pattern.charAt(d);
        if(c == '.' || c < x.c) collect(x.left, prefix, d, pattern, queue);
        if(c == '.' || c > x.c) collect(x.right, prefix, d, pattern, queue);
        if(c == '.' || c == x.c) {
            prefix.append(c);
            if(d == pattern.length() - 1 && x.val != null) queue.add(prefix.toString());
            if(d < pattern.length() - 1) collect(x.mid, prefix, d + 1, pattern, queue);
            prefix.deleteCharAt(prefix.length() - 1);   // very important, keep unchanged
        }

    }

    @Override
    public Iterable<String> keysWithPrefix(String s) {
        List<String> queue = new LinkedList<>();
        Node<Value> x = this.get(this.root, s, 0);
        if(x == null) return queue;
        if(x.val != null) queue.add(s);
        this.collect(x.mid, new StringBuilder(s), queue);
        return queue;
    }

    @Override
    public Iterable<String> keysThatMatch(String s) {
        List<String> queue = new LinkedList<>();
        this.collect(this.root, new StringBuilder(), 0, s, queue);
        return queue;
    }

    @Override
    public int size() {
        return this.n;
    }

    public static void main(String[] args) {
        TST<Integer> tst = new TST<>();
        tst.put("shell", 0);
        tst.put("she", 1);
        tst.put("shepherd", 2);
        for(String x: tst.keysWithPrefix("she"))
            System.out.println(x);
    }
}
