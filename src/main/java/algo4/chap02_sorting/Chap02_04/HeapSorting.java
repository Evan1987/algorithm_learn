package algo4.chap02_sorting.Chap02_04;

import algo4.chap02_sorting.Sorting;

/**
 * @author Evan
 * @date 2020/3/1 12:38
 */
public class HeapSorting extends Sorting {

    @Override
    public void sort(Comparable[] pq){
        int n = pq.length;

        // step 1: 构建堆
        // 任意一个数组也是堆，只不过是无序的
        // 数组右半元素都是叶子元素，无需扫描
        // 扫描左半元素进行sink，是对不同层级子堆进行有序化，由右至左是使扫描的子堆层级越来越高，最终使整个堆有序化
        for(int k = n / 2; k >= 1; k --)
            sink(pq, k, n);

        // step 2: 堆排序
        // 将根元素（当前最大的元素）置换至尾部，同时缩小堆大小，使得尾部元素脱离堆
        // 重新下沉排序，再次选举出当前最大的元素（次大）
        while(n > 1){
            exchange(pq, 1, n --);
            sink(pq, 1, n);
        }

    }

    /**
     * 下沉有序化
     * @param n 堆的大小
     * */
    private static void sink(Comparable[] pq, int k, int n){
        while(2 * k <= n){
            int j = 2 * k;
            if(j < n && less(pq, j, j + 1)) j ++;
            if(!less(pq, k, j)) break;
            exchange(pq, j, k);
            k = j;
        }
    }

    @SuppressWarnings("unchecked")
    private static boolean less(Comparable[] pq, int i, int j){
        return pq[i - 1].compareTo(pq[j - 1]) < 0;
    }

    public static void exchange(Object[] pq, int i, int j){
        Object temp = pq[i - 1];
        pq[i - 1] = pq[j - 1];
        pq[j - 1] = temp;
    }

    public static void main(String[] args) {
        String[] arr = {"s", "o", "r", "t", "e", "x", "a", "m", "p", "l", "e"};
        HeapSorting sorter = new HeapSorting();
        test(sorter, arr);
    }
}
