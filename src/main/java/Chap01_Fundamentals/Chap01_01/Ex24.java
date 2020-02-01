package Chap01_Fundamentals.Chap01_01;

public class Ex24 {
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
        System.out.println(gcd(105, 24));
        System.out.println(gcd(1111111, 1234567));
    }
}
