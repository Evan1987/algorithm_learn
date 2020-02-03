package Chap02_Sorting;

import Chap02_Sorting.Chap02_01.*;
import org.apache.commons.lang3.time.StopWatch;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import utils.URandom;
import utils.Array;


class SortTest {
    private URandom rnd;
    private int N;  // The length of array
    private int T;  // The times of testing

    public SortTest(Long seed, int N, int T){
        this.rnd = new URandom(seed);
        this.N = N;
        this.T = T;
    }

    private Double[] randomInput(){
        Double[] a = new Double[this.N];
        for(int i = 0; i < this.N; i ++)
            a[i] = this.rnd.nextDouble();
        return a;
    }

    private double timeTest(String alg, String method){
        Double[] a = randomInput();
        StopWatch watch = new StopWatch();
        watch.start();
        if(alg.equals("selection")){
            SelectionSort selectionSort = new SelectionSort();
            selectionSort.sort(a);
        }

        if(alg.equals("bubble")){
            BubbleSort bubbleSort = new BubbleSort();
            bubbleSort.sort(a);
        }

        if(alg.equals("insertion")){
            InsertionSort insertionSort = new InsertionSort();
            if(method.equals("withGuard")){
                insertionSort.sort(a, true);
            }

            if(method.equals("no exchange")){
                insertionSort.sortWithoutExchange(a);
            }

            if(method.equals("")){
                insertionSort.sort(a);
            }
        }

        if(alg.equals("shell")){
            ShellSort shellSort = new ShellSort();
            shellSort.sort(a);
        }

        if(alg.equals("array")){
            Arrays.sort(a);
        }

        watch.stop();
        return watch.getTime(TimeUnit.NANOSECONDS) / 1000000.0;  // ms
    }

    public double[] test(String alg, String method){
        double[] testLog = new double[this.T];
        for(int i = 0; i < this.T; i ++)
            testLog[i] = timeTest(alg, method);
        return testLog;
    }
}

public class SortCompare {
    public static void main(String[] args) {
        int N = 10000;
        int T = 5;
        Long seed = 2020L;
        test("selection", "", N, T, seed);                  // 83 +/- 21
        test("bubble", "", N, T, seed);                     // 205 +/- 10
        test("insertion", "", N, T, seed);                  // 84 +/- 4
        test("insertion", "withGuard", N, T, seed);         // 95 +/- 13
        test("insertion", "no exchange", N, T, seed);       // 60 +/- 29
        test("shell", "", N, T, seed);                      // 2.4 +/- 1.4
        test("array", "", N, T, seed);                      // 2.5 +/- 1.1
    }

    private static void test(String alg, String method, int N, int T, Long seed){
        SortTest sortTest = new SortTest(seed, N, T);
        double[] res = sortTest.test(alg, method);
        if(method.equals("")) method = "default";
        double mean = Array.average(res);
        double std = Array.std(res);
        System.out.printf("Tesing %d times on %d length.\n", T, N);
        System.out.printf("Alg. %s method %s: %.4f ms +/- %.4f ms\n", alg, method, mean, std);
    }

}
