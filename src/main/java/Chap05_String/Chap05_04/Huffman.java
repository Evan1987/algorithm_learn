package Chap05_String.Chap05_04;

import Chap02_Sorting.Chap02_04.MinPQ;
import org.jetbrains.annotations.NotNull;
import edu.princeton.cs.algs4.*;

/**
 * @author zhaochengming
 * @date 2020/10/10 09:35
 */
public class Huffman {
    private static final int R = 256;
    private Huffman(){}

    private static class Node implements Comparable<Node> {
        private final char c;
        private final int freq;
        private final Node left, right;

        Node(char c, int freq, Node left, Node right) {
            assert (left == null && right == null) || (left != null && right != null);
            this.c = c;
            this.freq = freq;
            this.left = left;
            this.right = right;
        }

        private boolean isLeaf() {
            return this.left == null && this.right == null;
        }

        @Override
        public int compareTo(@NotNull Node that) {
            return this.freq - that.freq;
        }
    }

    public static void compress() {
        String s = BinaryStdIn.readString();
        char[] input = s.toCharArray();

        // 统计字符频次
        int[] frequency = new int[R];
        for(char c: input) frequency[c] ++;

        // 建立 Huffman 前缀树
        Node root = buildTrie(frequency);

        // 建立码表
        String[] st = new String[R];
        buildCode(st, root, "");

        // 打印信息
        writeTrie(root);
        BinaryStdOut.write(input.length);

        // 利用Huffman编码输入
        for(char c: input) {
            String code = st[c];
            for(char x: code.toCharArray())
                if(x == '0') BinaryStdOut.write(false);
                else if(x == '1') BinaryStdOut.write(true);
                else throw new IllegalStateException("Illegal state");
        }

        BinaryStdOut.close();
    }

    private static Node buildTrie(int[] frequency) {
        // 利用小顶堆建立优先队列
        MinPQ<Node> pq = new MinPQ<>();
        for(char c = 0; c < R; c ++)
            if(frequency[c] > 0)
                pq.push(new Node(c, frequency[c], null, null));

        // 逐次合并两个频次最小的
        while(pq.size() > 1) {
            Node left = pq.pop();
            Node right = pq.pop();
            Node parent = new Node('\0', left.freq + right.freq, left, right);
            pq.push(parent);
        }
        return pq.pop();
    }

    private static void buildCode(String[] st, Node x, String s) {
        if(!x.isLeaf()) {
            buildCode(st, x.left, s + '0');
            buildCode(st, x.right, s + '1');
        }
        else
            st[x.c] = s;
    }

    private static void writeTrie(Node x) {
        if(x.isLeaf()) {
            BinaryStdOut.write(true);
            BinaryStdOut.write(x.c, 8);
            return;
        }
        BinaryStdOut.write(false);
        writeTrie(x.left);
        writeTrie(x.right);
    }

    public static void expand() {
        Node root = readTrie();
        int length = BinaryStdIn.readInt();

        for (int i = 0; i < length; i ++) {
            Node x = root;
            while (!x.isLeaf()) {
                boolean bit = BinaryStdIn.readBoolean();
                x = bit ? x.right : x.left;
            }
            BinaryStdOut.write(x.c, 8);
        }
        BinaryStdOut.close();
    }

    private static Node readTrie() {
        boolean isLeaf = BinaryStdIn.readBoolean();
        if(isLeaf)
            return new Node(BinaryStdIn.readChar(), -1, null, null);
        return new Node('\0', -1, readTrie(), readTrie());
    }

    public static void main(String[] args) {
        if              ("-".equals(args[0])) compress();
        else if         ("+".equals(args[0])) expand();
        else    throw new IllegalArgumentException("Illegal command line argument");
    }
}
