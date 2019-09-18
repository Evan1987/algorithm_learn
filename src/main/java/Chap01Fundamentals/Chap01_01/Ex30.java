package Chap01Fundamentals.Chap01_01;

import utils.StdIn;

public class Ex30 {
    private static int gcd(int p, int q){
        if(p == 0 || q == 0) return 1;
        if(p < q){  // 互换，保证p > q
            int t = p;
            p = q;
            q = t;
        }
        if(p % q == 0) return q;
        return gcd(q, p % q);
    }

    public static void main(String[] args){
        int N = StdIn.readInt();
        boolean[][] a = new boolean[N][N];
        for(int i = 0; i < N; i++){
            for(int j = i; j < N; j++){
                a[i][j] = gcd(i, j) == 1;
                System.out.print(a[i][j] + " ");
            }
            System.out.println();
        }
    }
}
