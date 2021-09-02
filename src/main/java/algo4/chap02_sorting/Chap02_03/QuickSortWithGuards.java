package algo4.chap02_sorting.Chap02_03;

import algo4.chap02_sorting.QuickSorting;
import utils.ArrayUtil;

import java.util.Random;

/**
 * @author Evan
 * @date 2020/2/29 10:12
 * Ex 2.3.17, 增加边界哨兵
 */
public class QuickSortWithGuards extends QuickSorting {

    @Override
    public void sort(Comparable[] a) {
        Random rnd = new Random();
        ArrayUtil.shuffle(a, rnd);
        int maxIndex = getMaxIndex(a);
        exchange(a, maxIndex, a.length - 1);  // put the max to the very end
        sort(a, 0, a.length - 1);
    }

    @Override
    public int partition(Comparable[] a, int lo, int hi) {
        int i = lo, j = hi + 1;
        Comparable v = a[lo];
        while(true){
            while(less(a[++ i], v));   // 如果是子数组，则右子数组的最左侧可作为哨兵保证循环退出
            while(less(v, a[-- j]));   // lo是左侧哨兵，可保证循环退出
            if(i >= j) break;
            exchange(a, i, j);
        }
        exchange(a, lo, j);
        return j;
    }

    private static int getMaxIndex(Comparable[] a){
        int max = 0;
        for(int i = 1; i < a.length; i ++){
            if(less(a[max], a[i])) max = i;
        }
        return max;
    }

    public static void main(String[] args) {
        String[] arr = {"a", "c", "f", "b", "g", "e", "d"};
        QuickSortWithGuards sorter = new QuickSortWithGuards();
        test(sorter, arr);
    }
}
