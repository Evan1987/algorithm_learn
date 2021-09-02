package baselearn;

import java.util.Scanner;

public class ScannerDemo {
    public static void main(String[] args){
        scan();
    }

    public static void scan(){
        Scanner scan = new Scanner(System.in);
        System.out.println("请输入数字， 输入end结束");
        double sum = 0.0;
        int num = 0;
        while (scan.hasNextDouble()){
            double x = scan.nextDouble();
            sum += x;
            num += 1;
        }

        System.out.printf("总输入数目为: %d\n", num);
        System.out.printf("平均值为: %.2f\n", sum / num);

        scan.close();
    }


}
