package Chap05_String.Chap05_03;

import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.Random;

/**
 * @author zhaochengming
 * @date 2020/10/9 17:33
 */
public class RabinKarpSearchImpl implements ISearch {
    private String pattern;                             // 只有拉斯维加斯算法需要保留
    private long patternHash;
    private long RM;                                    // 预计算， R^(m - 1)
    private final long Q = BigInteger.probablePrime(31, new Random()).longValue();
    private final int R = 256;

    public RabinKarpSearchImpl(){}
    public RabinKarpSearchImpl(@NotNull String pattern) {
        this.init(pattern);

    }

    private void init(String pattern) {
        this.pattern = pattern;
        int m = this.pattern.length();
        this.RM = 1;
        for (int i = 0; i < m - 1; i ++)
            this.RM = (this.RM * R) % this.Q;
        this.patternHash = this.hash(pattern, m);
    }

    /**
     * Horner哈希方法：除数取余
     * */
    private long hash(String s, int m) {
        long h = 0L;
        for (int i = 0; i < m; i ++)
            h = (this.R * h + s.charAt(i)) % this.Q;
        return h;
    }

    /**
     * 拉斯维加斯算法，在哈希命中后再次check
     * */
    private boolean check(String text, int i) {
        int m = this.pattern.length();
        for (int j = 0; j < m; j ++)
            if(this.pattern.charAt(j) != text.charAt(i + j))
                return false;
        return true;
    }

    @Override
    public int search(@NotNull String pattern, @NotNull String text) {
        if(!pattern.equals(this.pattern))
            this.init(pattern);
        return this.search(text);
    }

    public int search(@NotNull String text) {
        int n = text.length(), m = this.pattern.length();
        if(n < m) return n;
        long textHash = this.hash(text, m);
        if(textHash == this.patternHash && this.check(text, 0))
            return 0;

        // i 是 text的尾部指针
        for(int i = m; i < n; i ++) {
            // 减去第一个数字
            textHash = (textHash + this.Q - this.RM * text.charAt(i - m) % this.Q) % this.Q;
            // 增加新的数字
            textHash = (textHash * this.R + text.charAt(i)) % this.Q;

            // match
            int offset = i - m + 1;      //当前检查的text头部指针位置
            if(this.patternHash == textHash && this.check(text, offset))
                return offset;
        }
        return n;
    }

    public static void main(String[] args) {
        String text = "bcabcdababaabaabcbcabababacbacabeeacda";
        String pattern = "bcababab";
        RabinKarpSearchImpl search = new RabinKarpSearchImpl();
        System.out.println(search.search(pattern, text));
    }
}
