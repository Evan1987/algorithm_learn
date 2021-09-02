package algo4.chap01_fundamentals.Chap01_01;

public class Ex09 {
    private static String Int2Binary(int n){
        String s = "";
        for(int x = n; x > 0; x /= 2){
            s = (x % 2) + s;
        }
        return s;
    }

    public static void main(String[] args){
        int n = 10;
        System.out.printf("The bulitin method result: %s\n", Integer.toBinaryString(n));
        System.out.printf("The udf method result: %s\n", Int2Binary(n));
    }


}
