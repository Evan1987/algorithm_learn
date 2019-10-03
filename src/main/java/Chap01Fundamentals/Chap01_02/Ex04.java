package Chap01Fundamentals.Chap01_02;

public class Ex04 {
    private static void demo1(){
        String s1 = "hello";
        String s2 = s1;
        s1 = "world";
        System.out.printf("s1: %s, s2: %s", s1, s2);  // s1: world, s2: hello
    }

    public static void main(String [] args){
        demo1();
    }
}
