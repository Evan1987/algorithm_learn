package algo4.chap02_sorting.Chap02_03;

import algo4.chap02_sorting.QuickSorting;

/**
 * @author Evan
 * @date 2020/2/27 14:26
 */
public class QuickSort3Way extends QuickSorting {
    private int M = 10;

    public void setM(int M){
        this.M = M;
    }

    public int getM() {
        return this.M;
    }

    private void insertionSort(Comparable[] a, int lo, int hi) {
        for(int i = lo; i <= hi; i ++){
            for(int j = i; j > lo && less(a[j], a[j - 1]); j --)
                exchange(a, j, j - 1);
        }
    }

    @Override
    public void sort(Comparable[] a, int lo, int hi) {
        if(hi <= lo + this.M){
            insertionSort(a, lo, hi);
            return;
        }

        // 三向原地切分
        // a[lo ... lt - 1] < v = a[lt...gt] < a[gt + 1 ... hi]
        // lt： 指代与v相等的左侧游标，gt：指代与v相等的右侧游标
        // lt左侧都是小于v的元素，gt右侧都是大于v的元素
        // 初始认为 lt, gt就在两端点，随比较进行来移动
        int lt = lo, i = lo + 1, gt = hi;
        Comparable v = a[lo];
        while(i <= gt){                             // 大于gt截止，因为gt右侧确认大于v或越界
            int cmp = a[i].compareTo(v);
            if(cmp < 0) exchange(a, lt ++, i ++);    // 将小于v的元素移到左侧，同时滑动游标i（因为交换后a[i] == v），lt右移（因为左侧多了一个小于v的）
            else if(cmp > 0) exchange(a, i, gt --);  // 将大于v的元素移到右侧，不滑动游标i（因为交换后a[i]未知）,gt左移（因为右侧多了一个大于v的）
            else i ++;                              // 相等就只移动游标i
        }

        sort(a, lo, lt - 1);
        sort(a, gt + 1, hi);
    }

    @Override
    public int partition(Comparable[] a, int lo, int hi) {
        return 0;
    }

    public static void main(String[] args) {
        String[] a = {"a", "b", "d", "e", "c", "h", "j", "f", "g", "i"};
        QuickSort3Way sorter = new QuickSort3Way();
        sorter.setM(5);
        test(sorter, a);
    }
}
