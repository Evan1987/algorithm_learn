package algo4.chap03_searching.Chap03_02;

import java.util.Arrays;

/**
 * @author Evan
 * @date 2020/3/15 09:59
 * 完美平衡，与二分查找路径相同
 * Ex3.2.25
 */
public class Ex25 {
    public static void main(String[] args) {

    }


}


class BalanceTreeBuilder<Key extends Comparable<? super Key>> {
    private BST<Key, Integer> bst;
    public BalanceTreeBuilder(){}
    public BST<Key, Integer> build(Key[] a){
        bst = new BST<>();
        Arrays.sort(a);
        this.balance(bst, a, 0, a.length - 1);
        return bst;
    }

    private void balance(BST<Key, Integer> tree, Key[] a, int lo, int hi){
        if(lo > hi) return;
        int mid = lo + (hi - lo) / 2;
        tree.put(a[mid], mid);
        this.balance(tree, a, lo, mid - 1);
        this.balance(tree, a, mid + 1, hi);
    }
}
