package algo4.chap02_sorting.Chap02_05;

import org.apache.commons.lang3.ArrayUtils;

/**
 * @author Evan
 * @date 2020/3/8 10:19
 * Kendall Tau 距离计算
 */
public class Ex19 {
    public static void main(String[] args) {
        int[] a = {0, 3, 1, 6, 2, 5, 4};
        int[] b = {1, 0, 3, 6, 4, 2, 5};
        KendallTau kendallTau = new KendallTau(a);
        System.out.println("Kendall Tau distance: " + kendallTau.distance(b));
    }
}

class KendallTau {
    private int[] baseIndexes;  // 保存基准序列的排列顺序, 以mapping 视角：element -> index
    public KendallTau(int[] base){
        int n = base.length;
        this.baseIndexes = new int[n];
        boolean[] duplicatedHelper = new boolean[n];  // 用来辅助是否 base数组含有重复元素
        for(int i = 0; i < n; i ++){
            if(base[i] >= n)
                throw new IllegalArgumentException("The base array has element that beyond n");
            if(duplicatedHelper[base[i]])
                throw new IllegalArgumentException("The base array has duplicated elements");
            this.baseIndexes[base[i]] = i;
            duplicatedHelper[base[i]] = true;
        }
    }

    /**
     * 得到以base数组为基准的， other数组的index序列
     * */
    private int[] transform(int[] other){
        int n = other.length;
        assert n == this.baseIndexes.length;
        int[] indexes = new int[n];
        for(int i = 0; i < n; i ++)
            indexes[i] = this.baseIndexes[other[i]];  // 得到目标数组的序列
        return indexes;
    }

    /**
     * 计算Kendall Tau 距离
     * 相当于以base数组为基准，求other数组index序列的逆序数
     * */
    public int distance(int[] other){
        int[] indexes = this.transform(other);

        // 用 merge sort方法计算index序列的逆序数（元素交换数） Ex 2.2.19
        return algo4.chap02_sorting.Chap02_02.Ex19.count(ArrayUtils.toObject(indexes));
    }

}
