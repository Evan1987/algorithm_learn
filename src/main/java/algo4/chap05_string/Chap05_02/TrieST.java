package algo4.chap05_string.Chap05_02;


import java.util.*;

/**
 * @author zhaochengming
 * @date 2020/8/31 12:31
 */
public class TrieST<Value> extends AbstractStringST<Value> {
    private static final int R = 256;
    private Node root;                      // root of trie
    private int n;                          // num of keys

    private static class Node {
        private Object val;
        private Node[] next = new Node[R];
    }

    @Override
    public void put(String key, Value val) {
        if(val == null)   this.delete(key);
        else              this.root = this.put(this.root, key, val, 0);
    }

    private Node put(Node x, String key, Value val, int d){
        if(x == null) x = new Node();
        if(d == key.length()){
            if(x.val == null) this.n ++;  // 新增键值
            x.val = val;             // 值赋给最后一个端点
            return x;
        }
        char c = key.charAt(d);
        x.next[c] = this.put(x.next[c], key, val, d + 1);
        return x;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Value get(String key){
        Node x = this.get(this.root, key, 0);
        if(x == null) return null;
        return (Value) x.val;
    }

    private Node get(Node x, String key, int d){
        if(x == null) return null;
        if(d == key.length()) return x;
        char c = key.charAt(d);
        return this.get(x.next[c], key, d + 1);
    }

    @Override
    public int size() {
        return n;
    }

    @Override
    public Iterable<String> keysWithPrefix(String prefix) {
        List<String> results = new LinkedList<>();
        Node x = this.get(this.root, prefix, 0);
        this.collect(x, prefix, results);
        return results;
    }

    private void collect(Node x, String prefix, List<String> results){
        if(x == null) return;
        if(x.val != null) results.add(prefix);  // leaf node
        for(char c = 0; c < R; c ++)
            this.collect(x.next[c], prefix + c, results);

    }

    private void collect(Node x, String prefix, String pattern, List<String> results){
        if(x == null) return;
        int d = prefix.length();
        if(d == pattern.length()){
            if(x.val != null) results.add(prefix);
            return;
        }
        char c = pattern.charAt(d);
        if(c == '.')
            for(char ch = 0; ch < R; ch ++)
                this.collect(x.next[ch], prefix + ch, pattern, results);

        else
            this.collect(x.next[c], prefix + c, pattern, results);
    }

    @Override
    public Iterable<String> keysThatMatch(String pattern) {
        List<String> results = new LinkedList<>();
        this.collect(this.root, "", pattern, results);
        return results;
    }

    @Override
    public void delete(String key) {
        this.root = this.delete(this.root, key, 0);
    }

    private Node delete(Node x, String key, int d){
        if(x == null) return null;
        if(d == key.length()){
            if(x.val != null) this.n --;  // 删除了一个键值
            x.val = null;
        }
        else{
            char c = key.charAt(d);
            x.next[c] = this.delete(x.next[c], key, d + 1);
        }

        // remove the sub-tire rooted at x if it's completely empty
        if(x.val != null) return x;
        for(int c = 0; c < R; c ++)
            if(x.next[c] != null) return x;
        return null;
    }

    @Override
    public String longestPrefixOf(String query) {
        int length = this.longestPrefixOf(this.root, query, 0, -1);
        if(length == -1) return null;
        return query.substring(0, length);
    }

    // Return the length of the longest string key in the sub-trie rooted at x that is a prefix of query string
    private int longestPrefixOf(Node x, String query, int d, int length){
        if(x == null) return length;
        if(x.val != null) length = d;
        if(d == query.length()) return length;
        char c = query.charAt(d);
        return this.longestPrefixOf(x.next[c], query, d + 1, length);
    }

    public static void main(String[] args) {
        String s = "she sells sea shells by the sea shore";
        TrieST<Integer> st = new TrieST<>();
        String[] words = s.split(" ");
        for(int i = 0; i < words.length; i ++)
            st.put(words[i], i);

        System.out.println("ST's size: " + st.size());
        System.out.println("The value for key `sea`: " + st.get("sea"));
        System.out.println("longest prefix of `shellsort`: " + st.longestPrefixOf("shellsort"));
        System.out.println("longest prefix of `quicksort`: " + st.longestPrefixOf("quicksort"));
        System.out.print("keys with prefix `sh`: ");
        for(String key: st.keysWithPrefix("sh"))
            System.out.print(key + " ");
        System.out.println();

        System.out.print("keys with pattern `.he.l.`");
        for(String key: st.keysThatMatch(".he.l."))
            System.out.print(key + " ");
        System.out.println();

        st.delete("shore");
        System.out.print("deleted `shore`, the rest keys: ");
        for(String key: st.keys())
            System.out.print(key + " ");
    }

}
