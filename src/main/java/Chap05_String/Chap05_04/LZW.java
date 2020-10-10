package Chap05_String.Chap05_04;

import Chap05_String.Chap05_02.TST;
import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

/**
 * @author zhaochengming
 * @date 2020/10/10 11:25
 */
public class LZW {
    private static final int R = 256;           // 输入字符编码数
    private static final int L = 4096;          // 编码总数 = 2^w
    private static final int W = 12;            // 编码宽度

    private LZW(){}

    public static void compress() {
        String input = BinaryStdIn.readString();
        TST<Integer> st = new TST<>();
        for (int i = 0; i < R; i ++)
            st.put("" + (char) i, i);
        int code = R + 1;                               // make R as codeword for EOF

        while (input.length() > 0) {
            String s = st.longestPrefixOf(input);       // 寻找当前输入的最长前缀
            BinaryStdOut.write(st.get(s), W);           // encode s
            int t = s.length();
            if(t < input.length() && code < L)          // add s into st
                st.put(input.substring(0, t + 1), code ++);
            input = input.substring(t);
        }

        BinaryStdOut.write(R, W);
        BinaryStdOut.close();
    }

    public static void expand() {
        String[] st = new String[L];
        int i;                          // next available codeword value
        for (i = 0; i < R; i ++)
            st[i] = "" + (char) i;
        st[i++] = "";                   // look ahead for EOF
        int codeword = BinaryStdIn.readInt(W);
        if(codeword == R) return;
        String val = st[codeword];

        while (true) {
            BinaryStdOut.write(val);
            codeword = BinaryStdIn.readInt(W);
            if (codeword == R) break;
            String s = st[codeword];
            if(i == codeword) s = val + val.charAt(0);  // special case hack
            if(i < L) st[i ++] = val + s.charAt(0);
            val = s;
        }
        BinaryStdOut.close();
    }

    public static void main(String[] args) {
        if      (args[0].equals("-")) compress();
        else if (args[0].equals("+")) expand();
        else throw new IllegalArgumentException("Illegal command line argument");
    }
}
