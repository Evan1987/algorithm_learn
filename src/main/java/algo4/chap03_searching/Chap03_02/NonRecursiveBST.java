package algo4.chap03_searching.Chap03_02;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * @author Evan
 * @date 2020/3/10 13:37
 * A Binary Search Tree without recursive methods
 * Ex3.2.13,14
 */
public class NonRecursiveBST<Key extends Comparable<? super Key>, Value> extends BST<Key, Value> {

    private Node root;      // The root node for binary search tree.

    private class Node {
        private Key key;
        private Value val;
        private Node left, right;
        private int N;

        public Node(Key key, Value val, int N){
            this.key = key; this.val = val; this.N = N;
        }
    }

    @Override
    public Key min() {
        this.emptyCheck();
        Node x = this.root;
        while(x.left != null)
            x = x.left;
        return x.key;
    }

    @Override
    public Key max() {
        this.emptyCheck();
        Node x = this.root;
        while(x.right != null)
            x = x.right;
        return x.key;
    }

    @Override
    public Key floor(Key key) {
        this.nullKeyCheck(key);
        this.emptyCheck();

        Node x = this.root;
        Node best = null;
        while(x != null){
            int cmp = key.compareTo(x.key);
            if (cmp == 0) return x.key;
            if (cmp < 0) x = x.left;
            else {
                best = x;
                x = x.right;
            }
        }

        if(best == null) return null;
        return best.key;
    }

    @Override
    public Key ceiling(Key key) {
        this.nullKeyCheck(key);
        this.emptyCheck();

        Node x = this.root;
        Node best = null;
        while(x != null){
            int cmp = key.compareTo(x.key);
            if (cmp == 0) return x.key;
            if (cmp > 0) x = x.right;
            else {
                best = x;
                x = x.left;
            }
        }

        if(best == null) return null;
        return best.key;
    }

    @Override
    public int rank(Key key) {
        this.emptyCheck();
        this.nullKeyCheck(key);

        Node x = this.root;
        int index = 0;
        while(x != null){
            int cmp = key.compareTo(x.key);
            if      (cmp < 0) x = x.left;
            else if (cmp > 0){
                index += this.size(x.left) + 1;
                x = x.right;
            }
            else{
                index += this.size(x.left);
                break;
            }
        }
        return index;
    }

    private int size(Node x){
        if(x == null) return 0;
        return x.N;
    }

    @Override
    public Key select(int k) {
        this.emptyCheck();
        if(k < 0 || k > this.size()) throw new IllegalArgumentException("k is out of bound");

        Node x = this.root;
        int index = k;

        while(index > 0){
            int t = this.size(x.left);
            if(t < index){
                index -= t + 1;
                x = x.right;
            }
            else if(t > index){
                x = x.left;
            }
            else break;
        }
        return x.key;
    }

    @Override
    public Iterable<Key> keys(Key lo, Key hi) {
        this.emptyCheck();
        this.nullKeyCheck(lo);
        this.nullKeyCheck(hi);

        List<Key> queue = new LinkedList<>();
        if(lo.compareTo(hi) > 0) return queue;

        Stack<Node> nodes = new Stack<>();
        nodes.push(this.root);
        while(!nodes.isEmpty()){
            Node node = nodes.pop();
            if(node == null) continue;
            int cmpLo = lo.compareTo(node.key);
            int cmpHi = hi.compareTo(node.key);
            if(cmpLo <= 0 && cmpHi >= 0){   // lo --- node.key --- hi
                queue.add(node.key);
                nodes.push(node.left);
                nodes.push(node.right);
            }
            else if(cmpLo > 0){             // node.key -- lo --- hi
                nodes.push(node.right);
            }
            else nodes.push(node.left);     // lo --- hi -- node.key
        }
        return queue;
    }

    @Override
    public void put(Key key, Value value) {
        this.nullKeyCheck(key);
        if(value == null) {
            this.delete(key);
            return;
        }
        Node z = new Node(key, value, 1);
        if(this.root == null){
            this.root = z;
            return;
        }

        Node parent = null, x = this.root;
        int cmp = 0;

        // break if find the exact parent (when adding a new key)
        while(x != null){
            parent = x;
            cmp = key.compareTo(x.key);
            if      (cmp < 0) x = x.left;
            else if (cmp > 0) x = x.right;
            else {
                x.val = value;
                return;
            }
        }

        // judge where to add new node
        if(cmp < 0) parent.left = z;
        else        parent.right = z;

        // fix N value of nodes which are parents of z
        // just like `get`
        Node node = this.root;
        while(node != null){
            int cmp2 = key.compareTo(node.key);
            if(cmp2 == 0) break;
            node.N += 1;
            if(cmp2 < 0) node = node.left;
            else         node = node.right;
        }
    }

    @Override
    public Value get(Key key) {
        this.nullKeyCheck(key);
        Node x = this.root;
        while(x != null){
            int cmp = key.compareTo(x.key);
            if      (cmp < 0) x = x.left;
            else if (cmp > 0) x = x.right;
            return x.val;
        }
        return null;
    }

    @Override
    public int size() {
        if(this.root == null) return 0;
        return this.root.N;
    }
}
