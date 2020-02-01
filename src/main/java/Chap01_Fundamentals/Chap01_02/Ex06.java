package Chap01_Fundamentals.Chap01_02;

public class Ex06 {
    private static boolean isCircularRotation(String s, String t){
        return s.length() == t.length() && (s + s).contains(t);
    }

    public static void main(String [] args){
        String s = "ACTGACG";
        String t = "TGACGAC";
        System.out.println(isCircularRotation(s, t));  // true
    }
}
