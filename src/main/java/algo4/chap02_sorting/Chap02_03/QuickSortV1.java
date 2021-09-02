package algo4.chap02_sorting.Chap02_03;

import algo4.chap02_sorting.QuickSorting;

/**
 * @author Evan
 * @date 2020/2/26 16:51
 */

@Deprecated
public class QuickSortV1 extends QuickSorting {

    /**
     * 将数组切分为 a[lo... i - 1], a[i], a[i + 1 ... hi]
     * */
    @Override
    public int partition(Comparable[] a, int lo, int hi) {
        Comparable v = a[lo];  // 初始点
        int i = lo, j = hi + 1;

        // 确保左侧小于 v，右侧大于 v
        while(true){
            while(less(a[++ i], v) && i < hi);  // 在左侧找到比 v大的位置
            while(less(v, a[-- j]) && j > lo);  // 在右侧找到比 v小的位置
            if(i >= j) break;                   // 指针相遇，跳出
            exchange(a, i, j);
        }

        exchange(a, lo, j);  // 将a[j] 赋给切分点
        return j;
    }

    public static void main(String[] args) {
        String[] a = {"a", "b", "d", "e", "c", "h", "j", "f", "g", "i"};
        QuickSortV1 sorter = new QuickSortV1();
        test(sorter, a);
    }
}
