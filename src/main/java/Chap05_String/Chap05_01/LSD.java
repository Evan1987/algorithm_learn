package Chap05_String.Chap05_01;

/**
 * @author zhaochengming
 * @date 2020/8/16 21:48
 *
 */
public class LSD {
    public static void sort(String[] a, int w){
        int N = a.length;
        int R = 1 << 8;  // // extend ASCII alphabet size
        String[] aux = new String[N];
        for(int d = w - 1; d >= 0; d --){
            int[] count = new int[R + 1];
            for(String x: a)
                count[x.charAt(d) + 1] ++;
            for(int i = 0; i < R; i ++)
                count[i + 1] += count[i];
            for(String x: a)
                aux[count[x.charAt(d)] ++] = x;

            System.arraycopy(aux, 0, a, 0, N);
        }
    }

    public static void main(String[] args) {
        String[] licenses = {
                "4PGC938", "21YE230", "3CIO720", "1ICK750", "1OHV845",
                "4JZY524", "1ICK750", "3CIO720", "1OHV845", "1OHV845",
                "2RLA629", "2RLA629", "3ATW723"};
        LSD.sort(licenses, 7);
        for(String license: licenses)
            System.out.println(license);
    }
}
