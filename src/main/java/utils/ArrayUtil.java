package utils;

import java.util.*;
import org.apache.commons.lang3.ArrayUtils;

public class ArrayUtil {

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
        double sum = 0.0;
        for(double x: a){
            sum += x;
        }
        return sum / N;
    }

    // 计算数组标准差
    public static double std(double[] a){
        int N = a.length;
        double squareSum = 0;
        double sum = 0;
        for (double v : a) {
            squareSum += Math.pow(v, 2.0);
            sum += v;
        }

        double var = squareSum / N - Math.pow(sum / N, 2.0);
        return Math.sqrt(var);
    }

    public static <T> void swap(T[] a, int i, int j){
        T temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    // 反转数组元素 inplace
    public static <T> void reverse(T[] a){
        int N = a.length;
        for(int i = 0; i < N / 2; i ++){
            swap(a, i, N - 1 - i);
        }
    }

    /**
     * 随机打乱数组
     * */
    public static <T> void shuffle(T[] a, Random rnd){
        int N = a.length;
        for(int i = N; i > 1; i --)
            swap(a, i - 1, rnd.nextInt(i));
    }

    public static void main(String[] args){
        double[] x = {0.4, 0.6, 0.8, 0.9};
        System.out.printf("Max: %.2f, Avg: %.2f\n", max(x), average(x));
        Double[] x2 = ArrayUtils.toObject(x);
        reverse(x2);
        for(double y: x2){
            System.out.println(y);
        }

    }
}
