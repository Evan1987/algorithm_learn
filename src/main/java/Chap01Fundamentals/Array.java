package Chap01Fundamentals;

public class Array {

    // 寻找最大元素
    public static double max(double[] a){
        double max = a[0];
        for(int i = 1; i < a.length; i ++){
            if(a[i] > max) max = a[i];
        }
        return max;
    }

    // 计算数组元素平均值
    public static double average(double[] a){
        int N = a.length;
        Double sum = 0.0;
        for(Double x: a){
            sum += x;
        }
        return sum / N;
    }

    // 复制数组
    public static double[] copy(double[] a){
        int N = a.length;
        double[] b = new double[N];
        for(int i = 0; i < N; i++){
            b[i] = a[i];
        }
        return b;
    }

    // 反转数组元素 inplaced
    public static void reverse(double[] a){
        int N = a.length;
        for(int i = 0; i < N / 2; i ++){
            double temp = a[i];
            a[i] = a[N - 1 - i];
            a[N - 1 -i] = temp;
        }
    }

    public static void main(String[] args){
        double[] x = {0.4, 0.6, 0.8, 0.9};
        System.out.printf("Max: %.2f, Avg: %.2f\n", max(x), average(x));
        reverse(x);
        for(double y: x){
            System.out.println(y);
        }

    }
}
