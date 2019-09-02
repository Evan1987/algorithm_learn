package Chap01Fundamentals.Chap01_1;

public class Ex18 {
    private static int mystery(int a, int b){  // 乘法运算
        if(b == 0) return 0;
        if(b % 2 == 0) return mystery(a + a, b / 2);
        return mystery(a + a, b / 2) + a;
    }

    private static int mystery2(int a, int b){  // 乘方运算
        if(b == 0) return 1;
        if(b % 2 == 0) return mystery(a * a, b / 2);
        return mystery(a * a, b / 2) * a;
    }

    public static void main(String[] args){
        System.out.println(mystery(2, 25));
        System.out.println(mystery(3, 11));

        System.out.println(mystery2(2, 3));
        System.out.println(mystery2(3, 3));
    }
}
