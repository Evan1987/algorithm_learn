package Chap03_Searching.Chap03_03;

import Chap03_Searching.OrderedST;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * @author Evan
 * @date 2020/3/15 16:23
 */
public class RedBlackBST<Key extends Comparable<? super Key>, Value> extends OrderedST<Key, Value> {

    private static final boolean RED = true;
    private static final boolean BLACK = false;
    private Node root;  // the root of BST

    private class Node {
        Key key;
        Value val;
        Node left, right;
        int size;  // 子树中节点数
        boolean color;  // 与其父节点链接的颜色

        Node(Key key, Value val, int size, boolean color){
            this.key = key;
            this.val = val;
            this.size = size;
            this.color = color;
        }
    }

    private boolean isRed(Node x){
        if(x == null) return false;
        return x.color == RED;
    }

    // Number of nodes in subtree rooted at x;
    private int size(Node x){
        if(x == null) return 0;
        return x.size;
    }

    @Override
    public int size() {
        return this.size(this.root);
    }

    /***************************************************************************
     *  Standard BST search.
     ***************************************************************************/
    @Override
    public Value get(Key key) {
        this.nullKeyCheck(key);
        return this.get(this.root, key);
    }

    private Value get(Node x, Key key){
        while(x != null){
            int cmp = key.compareTo(x.key);
            if      (cmp < 0) x = x.left;
            else if (cmp > 0) x = x.right;
            else    return x.val;
        }
        return null;
    }

    public boolean contains(Key key){
        return this.get(key) != null;
    }

    /***************************************************************************
     *  Red-black tree insertion.
     ***************************************************************************/
    @Override
    public void put(Key key, Value value) {
        this.nullKeyCheck(key);
        if(value == null){
            this.delete(key);
            return;
        }

        this.root = this.put(this.root, key, value);
        root.color = BLACK;
    }

    // insert the k-v pair in the subtree rooted at h
    private Node put(Node h, Key key, Value value){
        if(h == null) return new Node(key, value, 1, RED);
        int cmp = key.compareTo(h.key);
        if      (cmp < 0)  h.left  = put(h.left,  key, value);
        else if (cmp > 0)  h.right = put(h.right, key, value);
        else               h.val   = value;

        // fix-up any right-leaning links
        if(isRed(h.right) && !isRed(h.left))       h = rotateLeft(h);   // 右红修正，左旋
        if(isRed(h.left) && isRed(h.left.left))    h = rotateRight(h);  // 连续两红 （4-节点）修正，以中间为根，右旋
        if(isRed(h.left) && isRed(h.right))        flipColors(h);       // 4-节点中央节点，翻转为三个 2-节点
        h.size = size(h.left) + size(h.right) + 1;                      // 重刷大小
        return h;
    }


    /***************************************************************************
     *  Red-black tree deletion.
     ***************************************************************************/
    public void deleteMin(){
        this.emptyCheck();
        if(!isRed(root.left) && !isRed(root.right))
            root.color = RED;                       // 方便利用 flipColors构造临时4节点

        this.root = deleteMin(this.root);
        if(!isEmpty()) root.color = BLACK;
    }

    // delete the k-v pair with the minimum key rooted at `h`
    private Node deleteMin(Node h){
        if(h.left == null) return null;  // `h` is just the minimum node
        if(!isRed(h.left) && !isRed(h.left.left))   // 左子节点为 2-节点（既不与父节点成为 3-节点，又不与子节点成为 3-节点），此为 2-节点判断条件
            h = moveRedLeft(h);                     // 将左子结点变为 3-节点

        h.left = deleteMin(h.left);                 // 继续在左侧删除
        return balance(h);                           // 向上回溯分解路径上的临时 4节点
    }

    public void deleteMax(){
        this.emptyCheck();
        if(!isRed(root.left) && !isRed(root.right))
            root.color = RED;                       // 方便利用 flipColors构造临时4节点

        this.root = deleteMax(this.root);
        if(!isEmpty()) root.color = BLACK;
    }

    private Node deleteMax(Node h){
        if(isRed(h.left))
            h = rotateRight(h);                         // 让右子节点变为 3节点

        if(h.right == null)
            return null;

        if(!isRed(h.right) && !isRed(h.right.left))     // 右子节点为 2-节点（同 deleteMin）
            h = moveRedRight(h);                        // 将右子结点变为 3-节点

        h.right = deleteMax(h.right);                   // 继续在右侧删除
        return balance(h);                              // 向上回溯分解路径上的临时 4节点
    }


    // Remove the specified key if the key is in the tree
    public void delete(Key key){
        this.nullKeyCheck(key);
        if(!contains(key)) return;

        if(!isRed(root.left) && !isRed(root.right))
            root.color = RED;

        root = delete(root, key);
        if(!isEmpty()) root.color = BLACK;
    }

    /**
     * Delete the k-v pair with the given key rooted at `h`
     * */
    private Node delete(Node h, Key key){
        // assert get(h, key) != null
        if(key.compareTo(h.key) < 0){
            // the deleting key is on the left
            if(!isRed(h.left) && !isRed(h.left.left))
                h = moveRedLeft(h);
            h.left = delete(h.left, key);
        }
        else{
            if(isRed(h.left))
                h = rotateRight(h);
            if(key.compareTo(h.key) == 0 && h.right == null)
                return null;
            if(!isRed(h.right) && !isRed(h.right.left))
                h = moveRedRight(h);
            if(key.compareTo(h.key) == 0){
                Node x = min(h.right);
                h.key = x.key;
                h.val = x.val;
                h.right = deleteMin(h.right);
            }
            else
                h.right = delete(h.right, key);
        }
        return balance(h);
    }

    /***************************************************************************
     *  Red-black tree helper functions.
     ***************************************************************************/
    // make a right-leaning link to the left 右红链接转为左红链接
    // 返回重置的节点
    private Node rotateLeft(Node h){
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = h.color;
        h.color = RED;
        x.size = h.size;
        h.size = size(h.left) + size(h.right) + 1;  // update size
        return x;
    }

    // make a left-leaning link to the right 左红链接转为右红链接
    // 返回重置的节点
    private Node rotateRight(Node h){
        Node x = h.left;
        h.left = x.right;
        x.color = h.color;
        x.right = h;
        h.color = RED;
        x.size = h.size;
        h.size = size(h.left) + size(h.right) + 1;  // update size
        return x;
    }

    // flip the colors of a node and its two children
    private void flipColors(Node h){
        // h must have opposite color of its two children
        assert (h != null) && (h.left != null) && (h.right != null);
        assert (!isRed(h) && isRed(h.left) && isRed(h.right)) || (isRed(h) && !isRed(h.left) && !isRed(h.right));
        h.color = !h.color;   // 红链接向上传
        h.left.color = !h.left.color;
        h.right.color = !h.right.color;
    }

    /**
     * Assuming that `h` is red and h.left and h.left.left are black, make h.left or one of its children red.
     * 就是让左子节点为三节点（或与父节点 h 成为 3-节点，或与子节点成为 3-节点）
     * */
    private Node moveRedLeft(Node h){
        // assert (h != null);
        // assert isRed(h) && !isRed(h.left) && !isRed(h.left.left);
        flipColors(h);  // h is black, h.left is red and h.right has opposite color.
        if(isRed(h.right.left)){              // 右子节点为 3-节点 -> 让左子节点与其子节点成为 3-节点
            h.right = rotateRight(h.right);  // 右旋，将 h.right.left移至 h.right
            h = rotateLeft(h);               // 左旋，
            flipColors(h);
        }
        // 否则 h 为 4-节点（左右链接均为红链接）
        return h;
    }

    /**
     * Assuming that `h` is red and both h.right and h.right.left are black, make h.right or one of its children red.
     * 就是让右子节点为三节点（或与父节点 h 成为 3-节点，或与子节点成为 3-节点）
     * */
    private Node moveRedRight(Node h){
        // assert (h != null);
        // assert isRed(h) && !isRed(h.right) && !isRed(h.right.left);
        flipColors(h);
        if(isRed(h.left.left)){ // 左子节点为 3-节点 -> 让右子节点与其子节点成为 3-节点
            h = rotateRight(h);
            flipColors(h);
        }
        // 否则 h 为 4-节点（左右链接均为红链接）
        return h;
    }

    // restore tree invariant
    private Node balance(Node h){
        if(isRed(h.right))                          h = rotateLeft(h);
        if(isRed(h.left) && isRed(h.left.left))     h = rotateRight(h);
        if(isRed(h.left) && isRed(h.right))         flipColors(h);
        h.size = size(h.left) + size(h.right) + 1;
        return h;
    }

    /***************************************************************************
     *  Utility functions.
     ***************************************************************************/
    public int height(){
        return height(this.root);
    }

    // Return the height of the BST rooted at node x.
    private int height(Node x){
        if(x == null) return -1;
        return 1 + Math.max(height(x.left), height(x.right));
    }


    /***************************************************************************
     *  Ordered symbol table methods.
     ***************************************************************************/

    @Override
    public Key min() {
        this.emptyCheck();
        return min(this.root).key;
    }

    // The smallest key in subtree rooted at `x`
    private Node min(Node x){
        if(x.left == null) return x;
        return min(x.left);
    }

    @Override
    public Key max() {
        this.emptyCheck();
        return max(this.root).key;
    }

    private Node max(Node x){
        if(x.right == null) return x;
        return max(x.right);
    }

    @Override
    public Key floor(Key key) {
        this.nullKeyCheck(key);
        this.emptyCheck();
        Node x = floor(this.root, key);
        if(x == null) throw new NoSuchElementException("argument to floor() is too small.");
        return x.key;
    }

    // get the largest key in subtree rooted at `x` less than or equal to the given key
    private Node floor(Node x, Key key){
        if(x == null) return null;
        int cmp = key.compareTo(x.key);
        if(cmp == 0)    return x;
        if(cmp < 0)     return floor(x.left, key);      // x.key is greater than key, then the floor may be in the left
        Node t = floor(x.right, key);                   // x.key is smaller than key, then the floor may be in the right or x itself
        if(t != null)   return t;
        return x;
    }

    @Override
    public Key ceiling(Key key) {
        this.nullKeyCheck(key);
        this.emptyCheck();
        Node x = ceiling(this.root, key);
        if(x == null) throw new NoSuchElementException("argument to ceiling() is too big.");
        return x.key;
    }

    // get the smallest key in subtree rooted at `x` greater than or equal to the given key
    private Node ceiling(Node x, Key key){
        if(x == null) return null;
        int cmp = key.compareTo(x.key);
        if(cmp == 0)    return x;
        if(cmp > 0)     return ceiling(x.right, key);   // x.key is smaller than key, then the ceiling may be in the right
        Node t = ceiling(x.left, key);                  // x.key is greater than key, then the ceiling may be in the left or x itself
        if(t != null) return t;
        return x;
    }

    @Override
    public int rank(Key key) {
        this.nullKeyCheck(key);
        return rank(this.root, key);
    }

    // the number of keys less than `key` in the subtree rooted at x
    private int rank(Node x, Key key){
        if(x == null) return 0;
        int cmp = key.compareTo(x.key);
        if(cmp < 0) return rank(x.left, key);       // x.key is greater than key, then the less-keys are all in left
        if(cmp > 0) return rank(x.right, key) + 1 + size(x.left);   // x.key is smaller than key, then the x.left and x itself are all less-keys
        return size(x.left);
    }

    @Override
    public Key select(int k) {
        if(k <= 0 || k >= this.size()) throw new IllegalArgumentException("argument to select() is invalid: " + k);
        Node x = select(this.root, k);
        return x.key;
    }

    // the key of rank `k` in the subtree rooted at `x`
    private Node select(Node x, int k){
        int t = size(x.left);
        if(t > k)   return select(x.left, k);
        if(t < k)   return select(x.right, k - t - 1);
        return x;
    }

    @Override
    public Iterable<Key> keys(Key lo, Key hi) {
        this.nullKeyCheck(lo);
        this.nullKeyCheck(hi);
        List<Key> queue = new LinkedList<>();
        this.addKeys(this.root, queue, lo, hi);
        return queue;
    }

    // add the keys between lo and hi in the subtree rooted at `x` to the queue
    private void addKeys(Node x, List<Key> queue, Key lo, Key hi){
        if(x == null) return;
        int cmpLo = lo.compareTo(x.key);
        int cmpHi = hi.compareTo(x.key);
        if(cmpLo < 0) addKeys(x.left, queue, lo, hi);   // there will be possible keys in x.left
        if(cmpLo >= 0 && cmpHi <= 0) queue.add(x.key);  // only x.key is possible
        if(cmpHi > 0) addKeys(x.right, queue, lo, hi);  // there will be possible keys in x.right
    }

    /***************************************************************************
     *  Check integrity of red-black tree data structure.
     ***************************************************************************/
    private boolean check(){
        if(!isBST())                    System.out.println("Not in symmetric order.");
        if(!isSizeConsistent())         System.out.println("Subtree counts not consistent.");
        if(!isRankConsistent())         System.out.println("Ranks not consistent.");
        if(!is23())                     System.out.println("Not a 2-3 tree.");
        if(!isBalanced())               System.out.println("Not balanced.");
        return isBST() && isSizeConsistent() && isRankConsistent() && is23() && isBalanced();
    }

    /**
     * Does this binary tree satisfy symmetric order
     * */
    private boolean isBST(){
        return isBST(this.root, null, null);
    }

    /**
     * Does the tree rooted at x satisfy symmetric order with all keys strictly between min and max
     * if `min` or `max` is null, treat as empty constraint
     * */
    private boolean isBST(Node x, Key min, Key max){
        if(x == null) return true;
        if(min != null && x.key.compareTo(min) <= 0) return false;  // min left on the right side
        if(max != null && x.key.compareTo(max) >= 0) return false;  // max left on the left side
        return isBST(x.left, min, x.key) && isBST(x.right, x.key, max);
    }

    /**
     * Are the `size` field correct?
     * */
    private boolean isSizeConsistent(){
        return isSizeConsistent(this.root);
    }

    private boolean isSizeConsistent(Node x){
        if(x == null) return true;
        if(x.size != size(x.left) + size(x.right) + 1) return false;
        return isSizeConsistent(x.left) && isSizeConsistent(x.right);
    }

    /**
     * Are the ranks correct?
     * */
    private boolean isRankConsistent(){
        for(int i = 0; i < size(); i ++)
            if(i != rank(select(i))) return false;
        for(Key key: keys())
            if(key.compareTo(select(rank(key))) != 0) return false;
        return true;
    }

    /**
     * Does the tree have no red right links and at most one red link in a row on any path?
     * */
    private boolean is23(){
        return is23(this.root);
    }

    private boolean is23(Node x){
        if(x == null) return true;
        if(isRed(x.right)) return false;
        if(x != this.root && isRed(x) && isRed(x.left)) return false;   // there are at least 2 red links on the path
        return is23(x.left) && is23(x.right);
    }

    /**
     * Do all paths from root to the leaf have the same number of black edges?
     * */
    private boolean isBalanced(){
        // find the num of black edges from the root to one leave
        int black = 0;
        Node x = this.root;
        while(x != null){
            if(!isRed(x)) black ++;
            x = x.left;
        }
        return isBalanced(this.root, black);
    }

    private boolean isBalanced(Node x, int black){
        if(x == null) return black == 0;
        if(!isRed(x)) black --;
        return isBalanced(x.left, black) && isBalanced(x.right, black);
    }

}
