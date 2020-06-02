package Chap05_String;

import java.util.Arrays;

/**
 * @author Evan
 * @date 2020/6/2 10:03
 */
public class AlphabetUtils {

    public static final Alphabet BINARY = new Alphabet("01");
    public static final Alphabet DNA = new Alphabet("ACTG");
    public static final Alphabet OCTAL = new Alphabet("01234567");
    public static final Alphabet DECIMAL = new Alphabet("0123456789");
    public static final Alphabet HEXADECIMAL = new Alphabet("0123456789ABCDEF");
    public static final Alphabet PROTEIN = new Alphabet("ACDEFGHIKLMNPQRSTVWY");
    public static final Alphabet LOWERCASE = new Alphabet("abcdefghijklmnopqrstuvwxyz");
    public static final Alphabet UPPERCASE = new Alphabet("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
    public static final Alphabet BASE64 = new Alphabet("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/");
    public static final Alphabet ASCII = new Alphabet(128);
    public static final Alphabet EXTENDED_ASCII = new Alphabet(256);
    public static final Alphabet UNICODE16 = new Alphabet(65536);

    private static class Alphabet {
        private char[] alphaBet;        // index -> char
        private int[] indices;          // char -> index
        private int R;                  // the num of characters

        private Alphabet(String s){
            this.R = s.length();
            this.alphaBet = s.toCharArray();
            this.indices = new int[Character.MAX_VALUE];
            Arrays.fill(this.indices, -1);

            boolean[] seen = new boolean[Character.MAX_VALUE];
            for(int i = 0; i < this.alphaBet.length; i ++){
                char c = this.alphaBet[i];
                if(seen[c]) throw new IllegalArgumentException("Duplicated character `" + c + "`");
                seen[c] = true;
                this.indices[c] = i;
            }
        }

        private Alphabet(int radix){
            this.R = radix;
            this.alphaBet = new char[radix];
            this.indices = new int[radix];
            for(int i = 0; i < radix; i ++){
                this.alphaBet[i] = (char) i;
                this.indices[i] = i;
            }
        }

        public Alphabet(){
            this(256);
        }

        private void validateIndex(int i){
            if(i < 0 || i >= this.R) throw new IllegalArgumentException("The index should between 0 and " + this.R);
        }

        private void validateCharacter(char c){
            if(c >= this.indices.length || this.indices[c] == -1)
                throw new IllegalArgumentException("Character " + c + " not in alphabet");
        }

        public char toChar(int index) {
            validateIndex(index);
            return this.alphaBet[index];
        }

        public int toIndex(char c){
            validateCharacter(c);
            return this.indices[c];
        }

        public boolean contains(char c){
            return this.indices[c] != -1;
        }

        public int R(){
            return this.R;
        }

        /**
         * x bits can only denote 2^x - 1
         * */
        public int lgR(){
            int lgR = 0;
            for(int t = this.R - 1; t >= 1; t /= 2)
                lgR ++;
            return lgR;
        }

        public int[] toIndices(String s){
            int[] result = new int[s.length()];
            char[] source = s.toCharArray();
            for(int i = 0; i < s.length(); i ++)
                result[i] = this.toIndex(source[i]);
            return result;
        }

        public String toChars(int[] indices){
            StringBuilder sb = new StringBuilder(indices.length);
            for(int x: indices)
                sb.append(this.toChar(x));
            return sb.toString();
        }
    }

    public static void main(String[] args) {
        String s = "HelloWorld";
        int[] indices = AlphabetUtils.BASE64.toIndices(s);
        for(int x: indices) System.out.print(x + " ");
    }
}
