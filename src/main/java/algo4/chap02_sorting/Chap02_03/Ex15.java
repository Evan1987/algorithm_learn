package algo4.chap02_sorting.Chap02_03;


/**
 * @author Evan
 * @date 2020/2/28 15:19
 * 螺丝、螺帽匹配问题。G.J.E.Rawlins
 */
public class Ex15 {
    public static void main(String[] args) {
        int[] a = {1, 3, 4, 2, 5, 10, 9, 8, 6, 7};
        int[] b = {4, 7, 2, 1, 3, 9, 10, 6, 5, 8};
        PairSorting sorter = new PairSorting();
        sorter.sort(a, b);
        display(a);
        display(b);

    }

    private static void display(int[] a){
        for(int x: a) System.out.print(x + " ");
        System.out.println();
    }
}


class PairSorting {
    public void sort(int[] q1, int[] q2){
        sort(q1, q2, 0, q1.length - 1);
    }

    // 先用q1的一个值v partition q2，得到对应的index，那么v在q1中也一定是index（这步可以节约一些时间）
    // 反过来利用q2[index] partition q1
    // 使得 q1, q2都具备如下性质
    // [lo ... index - 1] 都小于 v， [index + 1 ... hi] 都大于 v
    // 利用递归再sort两边即可

    private void sort(int[] q1, int[] q2, int lo, int hi){
        if(hi <= lo) return;
        int v = q1[lo];
        int index = this.partition(v, q2, lo, hi);
        exchange(q1, lo, index);  // q1对应的位置也一定是index
        this.partition(q2[index], q1, lo, hi);
        sort(q1, q2, lo, index - 1);
        sort(q1, q2, index + 1, hi);
    }

    /**
     * Partition `q` like QuickSorting V1
     * return the index of `v` in `q`.
     * */
    private int partition(int v, int[] q, int lo, int hi){
        int i = lo, j = hi;
        while(true){
            while(q[i] < v && i < hi) i ++;
            while(q[j] > v && j > lo) j --;
            if(i >= j) break;
            exchange(q, i, j);
        }
        return j;
    }

    private static void exchange(int[] a, int i, int j){
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}