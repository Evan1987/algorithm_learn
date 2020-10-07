package Chap05_String.Chap05_03;

/**
 * @author zhaochengming
 * @date 2020/10/5 09:40
 */
public class KMPSearchImpl implements ISearch {
    private int[] next;             // 表示不匹配时需要回退的模式指针位置
    private String pattern;

    private static int[] buildNext(String pattern) {
        int N = pattern.length();
        int[] next = new int[N];
        next[0] = -1;
        int i = 0, j = -1;      // i为主串指针，j为模式串指针，j永远比 i小
        while (i < N - 1) {
            if (j == -1 || pattern.charAt(i) == pattern.charAt(j)) {
                i ++;
                j ++;
                next[i] = j;
            }
            else j = next[j];
        }
        return next;
    }

    public KMPSearchImpl(){}
    public KMPSearchImpl(String pattern) {
        this.pattern = pattern;
        this.next = buildNext(pattern);
    }

    @Override
    public int search(String pattern, String text) {
        if(pattern == null) throw new IllegalArgumentException("Null pattern");
        if(!pattern.equals(this.pattern)) {
            this.pattern = pattern;
            this.next = buildNext(pattern);
        }
        return this.search(text);
    }

    public int search(String text) {
        if(this.pattern == null) throw new NullPointerException("Null pattern");
        int i = 0, j = 0;
        while (i < text.length() && j < this.pattern.length()) {
            if(j == -1 || text.charAt(i) == this.pattern.charAt(j)) {
                i ++;
                j ++;
            }
            else j = this.next[j];
        }
        if(j == this.pattern.length()) return i - j;
        return text.length();
    }

    public static void main(String[] args) {
        String text = "abababca";
        String pattern = "ababca";
        KMPSearchImpl search = new KMPSearchImpl();
        System.out.println(search.search(pattern, text));        // 2, ab^ababca
    }
}
