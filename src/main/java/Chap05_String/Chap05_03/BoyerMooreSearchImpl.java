package Chap05_String.Chap05_03;

import java.util.Arrays;

/**
 * @author zhaochengming
 * @date 2020/10/5 20:35
 */
public class BoyerMooreSearchImpl implements ISearch {
    private static final int R = 256;
    private String pattern;
    private int[] badCharIndexes;       // 坏字符索引
    private int[] goodSuffixIndexes;    // 好后缀索引

    private static int[] buildBadCharIndexes(String pattern) {
        int[] indexes = new int[R];
        Arrays.fill(indexes, -1);

        // 字符索引，右侧最近原则
        for(int i = 0; i < pattern.length(); i ++)
            indexes[pattern.charAt(i)] = i;
        return indexes;
    }

    private static int[] buildGoodSuffixIndexes(String pattern) {
        int N = pattern.length();
        int[] indexes = new int[N];
        Arrays.fill(indexes, -1);
        for(int i = 0; i < N - 1; i ++) {
            int j = i;
        }
    }

    private void init(String pattern) {
        this.pattern = pattern;
        this.badCharIndexes = buildBadCharIndexes(pattern);
    }

    public BoyerMooreSearchImpl(){}
    public BoyerMooreSearchImpl(String pattern) {
        this.init(pattern);
    }

}
