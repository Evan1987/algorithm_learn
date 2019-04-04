package Chap01Fundamentals;

public class Tests {

    private static void test1_1_8(){
        System.out.println('b');
        System.out.println('b' + 'c');
        System.out.println((char) ('a' + 4));
    }

    private static void test1_1_12(){
        int[] a = new int[10];
        for(int i = 0; i < 10; i++)
            a[i] = 9 - i;

        for(int i = 0; i < 10; i++)
            a[i] = a[a[i]];

        for(int i = 0; i < 10; i++)
            System.out.printf("%d: %d\n", i, a[i]);

    }


    public static void main(String [] args) {
        test1_1_12();
    }
}
