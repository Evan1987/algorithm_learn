package Chap01Fundamentals.Chap01_1;


// 返回不大于log2N的最大整数，禁用math库
public class Ex14 {
    public static void main(String[] args){
        int n = 1;
        System.out.println(calWithMath(n));
        System.out.println(calWithoutMath(n));
        System.out.println(calWithoutMath2(n));
    }

    private static double log2(double x){
        return Math.log(x) / Math.log(2.);
    }

    private static int calWithMath(double x){
        return (int)(log2(x));
    }

    /**
     * Cal with bit shift, to catch the left most `1` index
     * */
    private static int calWithoutMath(int n){
        int shiftRightCount = 0;
        do{
            //System.out.printf("N is %d, count is %d, bits is %s\n", n, shiftRightCount, Integer.toBinaryString(n));
            n >>= 1;
            shiftRightCount += 1;
        }while( n != 0);
        return shiftRightCount - 1;
    }

    /**
     * Cal with int division, to catch the left most `1` index, same as above.
     * */
    private static  int calWithoutMath2(int n){
        int i = 0;
        while(n > 1){
            n /= 2;
            i += 1;
        }
        return i;
    }
}
