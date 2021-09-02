package algo4.chap02_sorting.Chap02_03;

import algo4.chap02_sorting.QuickSorting;

/**
 * @author Evan
 * @date 2020/2/29 13:16
 * Ex 2.3.22 快速三向切分
 * Ex 2.3.23 Tukey's ninther method
 */
public class BentlyMcllroy3Way extends QuickSorting {

    private static int INSERTION_SORT_CUTOFF = 8;
    private static int MEDIAN_OF_3_CUTOFF = 40;

    @Override
    public void sort(Comparable[] a, int lo, int hi) {
        int n = hi - lo + 1;
        if(n < INSERTION_SORT_CUTOFF){
            insertionSort(a, lo, hi);
            return;
        }

        // use median-of-3 as partitioning element
        if(n < MEDIAN_OF_3_CUTOFF){
            int m = median3(a, lo, lo + n / 2, hi);
            exchange(a, m, lo);
        }
        // use Tukey ninther as partitioning element
        else {
            int eps = n / 8;
            int mid = lo + n / 2;
            int m1 = median3(a, lo, lo + eps, lo + eps + eps);
            int m2 = median3(a, mid - eps, mid, mid + eps);
            int m3 = median3(a, hi - eps - eps, hi - eps, hi);
            int m = median3(a, m1, m2, m3);
            exchange(a, m, lo);
        }


        Comparable v = a[lo];
        int p = lo, q = hi + 1;
        int i = p, j = q;

        //|---- =v ----|---- <v ----|---- >v ----|---- =v ----|
        //|lo ... p - 1|p .... i - 1|(ij).. q - 1|q ....... hi|
        while(true){
            // 移动左侧
            while(less(a[++ i], v) && i < hi);

            // 移动右侧
            while(less(v, a[-- j]) && j > lo);

            if(i == j && eq(a[i], v))
                exchange(a, i, ++ p);

            if(i >= j) break;

            exchange(a, i, j);

            // 处理相同值
            if(eq(a[i], v)) exchange(a, i, ++ p);
            if(eq(a[j], v)) exchange(a, j, -- q);
        }


        // 将相同值放在中间
        i = j + 1;
        for(int k = lo; k <= p; k ++)
            exchange(a, k, j --);
        for(int k = hi; k >= q; k --)
            exchange(a, k, i ++);

        sort(a, lo, j);
        sort(a, i, hi);

    }

    private static boolean eq(Comparable u, Comparable v){
        return u.compareTo(v) == 0;
    }

    private static int median3(Comparable[] a, int x, int y, int z){
        if(less(a[x], a[y])){
            if(less(a[y], a[z]))
                return y;
            if(less(a[z], a[x]))
                return x;
            return z;
        }

        if(less(a[z], a[y]))
            return y;
        if(less(a[x], a[z]))
            return x;
        return z;
    }

    @Override
    public int partition(Comparable[] a, int lo, int hi) {
        return 0;
    }

    private void insertionSort(Comparable[] a, int lo, int hi) {
        for(int i = lo; i <= hi; i ++){
            for(int j = i; j > lo && less(a[j], a[j - 1]); j --)
                exchange(a, j, j - 1);
        }
    }

    public static void main(String[] args) {
        String[] a = {"a", "b", "d", "a", "c", "a", "a", "f", "g", "i"};
        BentlyMcllroy3Way sorter = new BentlyMcllroy3Way();
        test(sorter, a);
    }
}
