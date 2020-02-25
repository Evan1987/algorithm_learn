package Chap02_Sorting.Chap02_02;

/**
 * @author Evan
 * @date 2020/2/25 19:21
 * 一式三份，从 3个列表中找到一个公共元素， O(N logN)
 * 先排序，再移动三指针寻找
 */
public class Ex21 {
    public static void main(String[] args) {
        String[] a = {"kz","ae","aa","ad","ac","al"};
        String[] b = {"bz","bb","aa","bd","bc","kz"};
        String[] c = {"cz","aa","kz","cd","cc","ab"};

        System.out.println(findRepeated(a, b, c));

    }

    private static <T extends Comparable<? super T>> T findRepeated(T[] q1, T[] q2, T[] q3){
        ImprovedMergeSort improvedMergeSort = new ImprovedMergeSort();
        improvedMergeSort.sort(q1);
        improvedMergeSort.sort(q2);
        improvedMergeSort.sort(q3);

        int N1 = q1.length, N2 = q2.length, N3 = q3.length;
        int index1 = 0, index2 = 0, index3 = 0;
        T max;

        while(index1 < N1 && index2 < N2 && index3 < N3){
            if(q1[index1] == q2[index2] && q2[index2] == q3[index3]) return q1[index1];
            max = maxThree(q1[index1], q2[index2], q3[index3]);
            while(index1 < N1 && less(q1[index1], max)) index1 ++;
            while(index2 < N2 && less(q2[index2], max)) index2 ++;
            while(index3 < N3 && less(q3[index3], max)) index3 ++;
        }
        return null;
    }

    private static <T extends Comparable<? super T>> T maxThree(T x, T y, T z){
        T max = x;
        if(max.compareTo(y) < 0)
            max = y;
        if(max.compareTo(z) < 0)
            max = z;
        return max;
    }

    private static <T extends Comparable<? super T>> boolean less(T x, T y){
        return x.compareTo(y) < 0;
    }
}
