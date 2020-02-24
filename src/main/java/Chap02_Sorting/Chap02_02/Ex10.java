package Chap02_Sorting.Chap02_02;

import Chap02_Sorting.*;

import java.util.Random;
import java.util.stream.Collectors;

/**
 * @author Evan
 * @date 2020/2/24 13:42
 *
 * 快速归并，merge时不检查边界是否用尽，但会使排序不稳定（相等顺序因一方用尽而被打乱）
 */
public class Ex10 {

    public static void main(String[] args) {
        Random rnd = new Random(0);
        Integer[] arr = rnd.ints(10000, 0, 10000)
                .boxed().toArray(Integer[]::new);
        Merge merge = new Merge();
        Sorting.test(merge, arr);
    }
}

class Merge extends TopDownMergeSortV2 {
    @Override
    public void merge(Comparable[] a, int lo, int mid, int hi, Comparable[] aux){
        int i = lo, j = hi;

        // 顺序复制左半边
        System.arraycopy(a, lo, aux, lo, mid - lo + 1);

        // 逆序复制右半边
        for(int k = mid + 1; k <= hi; k ++)
            aux[k] = a[hi + mid + 1 - k];

        // 当前aux的状态
        // |a[lo],a[lo+1],... a[mid] | a[hi],a[hi-1],...,a[mid+1]|
        // | --------- 升序 --------> | --------- 降序 ---------> |
        for(int k = lo; k <= hi; k ++){
            a[k] = less(aux[j], aux[i]) ? aux[j --] : aux[i ++];
        }
    }
}
