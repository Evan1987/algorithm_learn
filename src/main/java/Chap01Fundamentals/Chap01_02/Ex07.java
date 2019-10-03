package Chap01Fundamentals.Chap01_02;

public class Ex07 {
    private static String mystery(String s){
        int N = s.length();
        if(N <= 1) return s;
        String a = s.substring(0, N / 2);
        String b = s.substring(N / 2, N);
        return mystery(a) + mystery(b);
    }

    public static void main(String [] args){
        System.out.println(mystery("abcdefg"));  // return it self
    }

}
