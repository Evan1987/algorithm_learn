package Chap06_Context.btree;

import org.jetbrains.annotations.NotNull;

/**
 * @author zhaochengming
 * @date 2020/10/10 19:09
 */
public class BTree<Key extends Comparable<Key>, Value> {

    // max children per B-tree node = M-1
    // (must be even and greater than 2)
    private static final int M = 4;
    private Node<Key, Value> root;                                      // root of B-tree
    private int height;                                                 // height if B-tree
    private int n;                                                      // num of kv pairs in B-tree

    // B-tree node data type
    private static final class Node<K extends Comparable<K>, V> {
        private int m;                                                              // num of children
        private Entry<K, V>[] children = new Entry[M];                             // array of children

       // create a Node with k children
        private Node(int k) {
            this.m = k;
        }
    }

    // For internal nodes: only use key and next
    // For external nodes: only use key and val
    private static class Entry<K extends Comparable<K>, V> {
        private K key;
        private V val;
        private Node<K, V> next;
        public Entry(K k, V v, Node<K, V> next) {
            this.key = k;
            this.val = v;
            this.next = next;
        }
    }

    public BTree() {
        this.root = new Node<>(0);
    }

    public boolean isEmpty() {
        return this.n == 0;
    }

    public int size() {
        return this.n;
    }

    public int height() {
        return this.height;
    }

    public Value get(@NotNull Key key) {
        return this.search(this.root, key, this.height);
    }

    public void put(@NotNull Key key, Value val) {
        Node<Key, Value> u = this.insert(this.root, key, val, this.height);
        this.n ++;
        if(u == null) return;

        // need to split root
        Node<Key, Value> t = new Node<>(2);
        t.children[0] = new Entry<>(root.children[0].key, null, root);
        t.children[1] = new Entry<>(u.children[0].key, null, u);
        this.root = t;
        this.height ++;
    }

    private Value search(Node<Key, Value> x, Key key, int ht) {
        Entry<Key, Value>[] children = x.children;

        // external node
        if(ht == 0)
            for (Entry<Key, Value> e: children)
                if(key.compareTo(e.key) == 0) return e.val;

        // internal node
        else
            for(int j = 0; j < x.m; j ++)
                if(j + 1 == x.m || key.compareTo(children[j + 1].key) < 0)
                    return this.search(children[j].next, key, ht - 1);

        return null;
    }

    private Node<Key, Value> insert(Node<Key, Value> h, Key key, Value val, int ht) {
        int j = 0;
        Entry<Key, Value> t = new Entry<>(key, val, null);

        // external node
        if(ht == 0)
            for (j = 0; j < h.m; j ++)
                if(key.compareTo(h.children[j].key) < 0) break;

        // internal node
        else
            for(j = 0; j < h.m; j ++)
                if(j + 1 == h.m || key.compareTo(h.children[j + 1].key) < 0) {
                    Node<Key, Value> u = this.insert(h.children[j ++].next, key, val, ht - 1);
                    if(u == null) return null;
                    t.key = u.children[0].key;
                    t.next = u;
                    break;
                }

        if (j <= h.m) System.arraycopy(h.children, j, h.children, j + 1, h.m - j);
        h.children[j] = t;
        h.m ++;
        if(h.m < M) return null;
        return this.split(h);
    }

    // split node to half
    private Node<Key, Value> split(Node<Key, Value> h) {
        Node<Key, Value> t = new Node<>(M / 2);
        h.m = M / 2;
        System.arraycopy(h.children, M / 2, t.children, 0, M / 2);
        return t;
    }

    public static void main(String[] args) {
        BTree<String, String> st = new BTree<String, String>();

        st.put("www.cs.princeton.edu", "128.112.136.12");
        st.put("www.cs.princeton.edu", "128.112.136.11");
        st.put("www.princeton.edu",    "128.112.128.15");
        st.put("www.yale.edu",         "130.132.143.21");
        st.put("www.simpsons.com",     "209.052.165.60");
        st.put("www.apple.com",        "17.112.152.32");
        st.put("www.amazon.com",       "207.171.182.16");
        st.put("www.ebay.com",         "66.135.192.87");
        st.put("www.cnn.com",          "64.236.16.20");
        st.put("www.google.com",       "216.239.41.99");
        st.put("www.nytimes.com",      "199.239.136.200");
        st.put("www.microsoft.com",    "207.126.99.140");
        st.put("www.dell.com",         "143.166.224.230");
        st.put("www.slashdot.org",     "66.35.250.151");
        st.put("www.espn.com",         "199.181.135.201");
        st.put("www.weather.com",      "63.111.66.11");
        st.put("www.yahoo.com",        "216.109.118.65");


        System.out.println("cs.princeton.edu:  " + st.get("www.cs.princeton.edu"));
        System.out.println("hardvardsucks.com: " + st.get("www.harvardsucks.com"));
        System.out.println("simpsons.com:      " + st.get("www.simpsons.com"));
        System.out.println("apple.com:         " + st.get("www.apple.com"));
        System.out.println("ebay.com:          " + st.get("www.ebay.com"));
        System.out.println("dell.com:          " + st.get("www.dell.com"));
        System.out.println();

        System.out.println("size:    " + st.size());
        System.out.println("height:  " + st.height());
    }
}
