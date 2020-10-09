package Chap05_String.Chap05_03;

import org.jetbrains.annotations.NotNull;

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

    /**
     * 坏字符的最右索引，如果未出现则为 -1
     * */
    private static int[] buildBadCharIndexes(String pattern) {
        int[] indexes = new int[R];
        Arrays.fill(indexes, -1);

        // 字符索引，右侧最近原则
        // 最后一位必须略过，因为需要判断坏字符在pattern最后一位之前是否出现
        for(int i = 0; i < pattern.length() - 1; i ++)
            indexes[pattern.charAt(i)] = i;
        return indexes;
    }

    /**
     * 好后缀索引，由于固定右端点索引位置为 N-1，因此可根据左端点位置建立索引，分为 3个case：
     * case1：有子串与好后缀完全匹配；
     * case2：前缀部分与好后缀部分匹配（包含部分case1情形）；
     * case3：不存在以上情形。
     * 需要依赖辅助数组 suffix[]帮助计算。
     * @return 好后缀需要移动到的 pattern指针位置
     * */
    private static int[] buildGoodSuffixIndexes(String pattern) {
        int M = pattern.length();
        int[] indexes = new int[M];     // 标识好后缀需要移动到的位置
        int[] suffix = buildHelperSuffixLength(pattern);

        // 初始化并包含case3情形
        Arrays.fill(indexes, -1);

        // 考虑 case2情形，前缀匹配，要求尽可能让前缀匹配得更长，因此倒着循环
        // 另外好后缀的不匹配点一定大于 N - 1
        int j = 0;
        for(int i = M - 2; i >= 0; i --)
            // 前缀匹配的标志
            if(suffix[i] == i + 1)
                // 由于是不完全后缀匹配，因此 j != N - 1 - i
                // 另外不重新设置 j 可跳过之前更新的部分
                for(; j < M - 1 - i; j ++)
                    if(indexes[j] == -1)
                        indexes[j] = i;

        // 考虑case1情形，完全匹配
        for(int i = 0; i < M - 1; i ++)
            indexes[M - 1 - suffix[i]] = i;

        return indexes;
    }

    /**
     * suffix数组的暴力计算版本
     * */
    private static int[] buildHelperSuffixLengthBrute(String pattern) {
        int M = pattern.length();
        int[] suffix = new int[M];
        suffix[M - 1] = M;

        // 暴力计算
        for(int i = M - 2, j = i; i >= 0; i --) {
            while(j >= 0 && pattern.charAt(j) == pattern.charAt(M - 1 - (i - j)))
                j --;
            suffix[i] = i - j;
        }
        return suffix;
    }

    /**
     * 利用已经计算的 suffix length信息加速计算
     * 对于每个指针 i，suffix[i]表示 pattern[:(i + 1)] 与 pattern的公共后缀长度
     * */
    private static int[] buildHelperSuffixLength(String pattern) {
        int M = pattern.length();
        int[] suffix = new int [M];
        suffix[M - 1] = M;
        int f = 0, g = M - 1;
        for(int i = M - 2; i >= 0; i --) {
            // 利用之前的匹配信息
            if(i > g && suffix[M - 1 - (f - i)] < i - g)
                suffix[i] = suffix[M - 1 - (f - i)];
            else{
                if(i < g) g = i;
                // 采用暴力方式
                f = i;
                while(g >= 0 && pattern.charAt(g) == pattern.charAt(M - 1 - (i - g)))
                    g --;
                suffix[i] = f - g;
            }
        }

        return suffix;
    }

    private void init(String pattern) {
        this.pattern = pattern;
        this.badCharIndexes = buildBadCharIndexes(pattern);
        this.goodSuffixIndexes = buildGoodSuffixIndexes(pattern);

    }

    public BoyerMooreSearchImpl(){}
    public BoyerMooreSearchImpl(String pattern) {
        this.init(pattern);
    }

    @Override
    public int search(@NotNull String pattern, @NotNull String text) {
        if(!pattern.equals(this.pattern)) this.init(pattern);
        return this.search(text);
    }

    public int search(@NotNull String text) {
        int n = text.length(), m = this.pattern.length();
        int s = 0, j;  // s为text字符指针，j为pattern字符指针
        while(s <= n - m) {

            j = m - 1;
            while(j >= 0 && pattern.charAt(j) == text.charAt(s + j))
                j --;

            // matched
            if(j < 0) return s;

            // 坏字符 && 好后缀
            s += Math.max(j - this.badCharIndexes[text.charAt(s + j)], m - 1 - this.goodSuffixIndexes[j]);
        }
        return n;
    }

    public static void main(String[] args) {
        String[] texts = {"abbadcababacab", "bcabcdababaabaabcbcabababacbacabeeacda", "dieiahgjkriabddioababa"};
        String[] patterns = {"babac", "bcababab", "eigha"};
        BoyerMooreSearchImpl bm = new BoyerMooreSearchImpl();
        for(int i = 0; i < texts.length; i ++) {
            System.out.println("Text: " + texts[i]);
            System.out.println("Pattern: " + patterns[i]);
            System.out.println(bm.search(patterns[i], texts[i]));
        }
    }
}
