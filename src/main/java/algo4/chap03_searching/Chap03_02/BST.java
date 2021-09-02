package algo4.chap03_searching.Chap03_02;

import algo4.chap03_searching.OrderedST;
import algo4.chap03_searching.ST;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * @author Evan
 * @date 2020/3/9 16:39
 */
public class BST<Key extends Comparable<? super Key>, Value> extends OrderedST<Key, Value> {

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
    public void put(Key key, Value value) {
        this.nullKeyCheck(key);
        if(value == null) {
            this.delete(key);
            return;
        }

        this.root = this.put(this.root, key, value);
    }

    private Node put(Node x, Key key, Value val){
        if(x == null) return new Node(key, val, 1);
        int cmp = key.compareTo(x.key);
        // 重置路径上的指向关系，并增加路径上各节点的计数
        if      (cmp < 0) x.left = this.put(x.left, key, val);
        else if (cmp > 0) x.right = this.put(x.right, key, val);
        else x.val = val;
        x.N = this.size(x.left) + this.size(x.right) + 1;  // 重刷
        return x;
    }

    @Override
    public Value get(Key key) {
        this.nullKeyCheck(key);
        return this.get(this.root, key);
    }

    private Value get(Node x, Key key){
        if(x == null) return null;
        int cmp = key.compareTo(x.key);
        if(cmp < 0) return this.get(x.left, key);
        if(cmp > 0) return this.get(x.right, key);
        return x.val;
    }

    @Override
    public int size() {
        return this.size(root);
    }

    private int size(Node x){
        if(x == null) return 0;
        return x.N;
    }

    @Override
    public void deleteMin() {
        this.emptyCheck();
        this.root = this.deleteMin(this.root);
    }

    private Node deleteMin(Node x){
        if(x.left == null) return x.right;
        x.left = this.deleteMin(x.left);
        x.N = this.size(x.left) + this.size(x.right) + 1;
        return x;
    }

    @Override
    public void deleteMax() {
        this.emptyCheck();
        this.root = this.deleteMax(this.root);
    }

    private Node deleteMax(Node x){
        if(x.right == null) return x.left;
        x.right = this.deleteMax(x.right);
        x.N = this.size(x.left) + this.size(x.right) + 1;
        return x;
    }

    @Override
    public void delete(Key key) {
        this.nullKeyCheck(key);
        this.root = this.delete(this.root, key);
    }

    private Node delete(Node x, Key key){
        if(x == null) return null;
        int cmp = key.compareTo(x.key);
        if      (cmp < 0) x.left = this.delete(x.left, key);
        else if (cmp > 0) x.right = this.delete(x.right, key);
        else {
            // delete node x and re-generate a new node
            if(x.left == null) return x.right;
            if(x.right == null) return x.left;
            Node t = x;  // temp copy

            // The new x node will be the smallest one of the right side
            // which makes the right nodes remained keep bigger than x
            // ** Another method is to select the biggest one of the left side.
            x = this.min(t.right);
            x.right = this.deleteMin(t.right);  // delete the one from the right side
            x.left = t.left;
        }
        x.N = this.size(x.left) + this.size(x.right) + 1;
        return x;
    }

    @Override
    public Key min() {
        this.emptyCheck();
        return this.min(this.root).key;
    }

    private Node min(Node x){
        if(x.left == null) return x;
        return this.min(x.left);
    }

    @Override
    public Key max() {
        this.emptyCheck();
        return this.max(this.root).key;
    }

    private Node max(Node x){
        if(x.right == null) return x;
        return this.max(x.right);
    }

    @Override
    public Key floor(Key key) {
        this.nullKeyCheck(key);
        this.emptyCheck();
        Node x = this.floor(this.root, key);
        if(x == null) throw new NoSuchElementException("there is no available key which is the floor");
        return x.key;
    }

    private Node floor(Node x, Key key){
        if(x == null) return null;
        int cmp = key.compareTo(x.key);
        if(cmp == 0) return x;

        // key < x's key, find the floor key on the left side
        // maybe not find the node which smaller than key, return null
        if(cmp < 0) return this.floor(x.left, key);

        // key > x's key, find the floor key on the right side
        // if not find, then x is the biggest node that smaller than key
        Node t = this.floor(x.right, key);
        if(t != null) return t;
        return x;
    }

    /**
     * Another method to find floor key, to keep the same method recursively on both left and right side.
     * */
    public Key floor2(Key key){
        this.nullKeyCheck(key);
        this.emptyCheck();
        Key k = this.floor2(this.root, key, null);
        if(k == null) throw new NoSuchElementException("there is no available key which is the floor");
        return k;
    }

    private Key floor2(Node x, Key key, Key best){
        if(x == null) return best;
        int cmp = key.compareTo(x.key);
        // key < x's key find the suitable key on the left side
        if(cmp < 0) return this.floor2(x.left, key, best);
        // key > x's key, x's key is now a suitable key, then find a better one on the right side
        if(cmp > 0) return this.floor2(x.right, key, x.key);
        return x.key;
    }

    @Override
    public Key ceiling(Key key) {
        this.nullKeyCheck(key);
        this.emptyCheck();
        Node x = this.ceiling(this.root, key);
        if(x == null) throw new NoSuchElementException("there is no available key which is the ceiling");
        return x.key;
    }

    private Node ceiling(Node x, Key key){
        if(x == null) return null;
        int cmp = key.compareTo(x.key);
        if(cmp == 0) return x;
        // key > x's key
        if(cmp > 0) return this.ceiling(x.right, key);

        // key < x's key
        Node t = this.ceiling(x.left, key);
        if(t != null) return t;
        return x;
    }

    @Override
    public int rank(Key key) {
        this.nullKeyCheck(key);
        return this.rank(this.root, key);
    }

    private int rank(Node x, Key key){
        if(x == null) return 0;
        int cmp = key.compareTo(x.key);
        if(cmp < 0) return this.rank(x.left, key);
        if(cmp > 0) return 1 + this.size(x.left) + this.rank(x.right, key);
        return this.size(x.left);
    }

    @Override
    public Key select(int k) {
        if(k < 0 || k > this.size()) throw new IllegalArgumentException("k is out of bound");
        return this.select(this.root, k).key;
    }

    private Node select(Node x, int k){
        if(x == null) return null;
        int t = this.size(x.left);  // the very index of x
        // there are more nodes than k on left, then find the kth node on the left
        if(t > k) return this.select(x.left, k);
        // there are less nodes than k on left, then find the (k - t - 1)th node on the right
        if(t < k) return this.select(x.right, k - t - 1);
        return x;
    }

    @Override
    public Iterable<Key> keys(Key lo, Key hi) {
        this.nullKeyCheck(lo);
        this.nullKeyCheck(hi);
        List<Key> queue = new LinkedList<>();
        this.keys(this.root, queue, lo, hi);
        return queue;
    }

    private void keys(Node x, List<Key> queue, Key lo, Key hi){
        if(x == null) return;
        int cmpLo = lo.compareTo(x.key);
        int cmpHi = hi.compareTo(x.key);
        if(cmpLo <= 0 && cmpHi >= 0) queue.add(x.key);
        if(cmpLo < 0) this.keys(x.left, queue, lo, hi);
        if(cmpHi > 0) this.keys(x.right, queue, lo, hi);
    }

    public int height(){
        return this.height(this.root);
    }

    private int height(Node x){
        if(x == null) return -1;
        return 1 + Math.max(this.height(x.left), this.height(x.right));
    }

    // Added by Ex 3.2.7
    public double avgCompares(){
        return ((double) this.depthSum(this.root, 1)) / this.root.N;
    }

    /**
     * Sum over all the tree nodes' (include self) depth
     * */
    private int depthSum(Node x, int depth){
        if(x == null) return 0;
        return this.depthSum(x.left, depth + 1) + this.depthSum(x.right, depth + 1) + depth;
    }

    /**
     * Return the keys by the level order, breadth-first-search
     * */
    public Iterable<Key> levelOrderKeys(){
        List<Key> keys = new LinkedList<>();
        Stack<Node> nodes = new Stack<>();
        nodes.push(this.root);
        while(!nodes.isEmpty()){
            Node node = nodes.pop();
            if(node == null) continue;
            keys.add(node.key);
            nodes.push(node.left);
            nodes.push(node.right);
        }
        return keys;
    }

    public static void main(String[] args) {
        ST.test(new BST<>());
    }

}
