package Chap02_Sorting;

import Chap02_Sorting.Chap02_01.*;
import Chap02_Sorting.Chap02_02.BottomUpMergeSort;
import Chap02_Sorting.Chap02_02.TopDownMergeSort;
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

    private double timeTest(SortMethodEnum alg, String method){
        Double[] a = randomInput();
        StopWatch watch = new StopWatch();
        watch.start();

        switch(alg){
            case SELECTION:
                SelectionSort selectionSort = new SelectionSort();
                selectionSort.sort(a);
                break;

            case BUBBLE:
                BubbleSort bubbleSort = new BubbleSort();
                bubbleSort.sort(a);
                break;

            case INSERTION:
                InsertionSort insertionSort = new InsertionSort();
                if(method.equals("withGuard")){
                    insertionSort.sort(a, true);
                }

                else if(method.equals("no exchange")){
                    insertionSort.sortWithoutExchange(a);
                }

                else{
                    insertionSort.sort(a);
                }
                break;

            case SHELL:
                ShellSort shellSort = new ShellSort();
                shellSort.sort(a);
                break;

            case TOP_DOWN_MERGE:
                TopDownMergeSort topDownMergeSort = new TopDownMergeSort();
                topDownMergeSort.sort(a);
                break;

            case BOTTOM_UP_MERGE:
                BottomUpMergeSort bottomUpMergeSort = new BottomUpMergeSort();
                bottomUpMergeSort.sort(a);
                break;

            case QUICK:
                Arrays.sort(a);
                break;
        }

        watch.stop();
        return watch.getTime(TimeUnit.NANOSECONDS) / 1000000.0;  // ms
    }

    public double[] test(SortMethodEnum alg, String method){
        double[] testLog = new double[this.T];
        for(int i = 0; i < this.T; i ++)
            testLog[i] = timeTest(alg, method);
        return testLog;
    }
}

/**
 * Must disable {@link utils.annotations.WatchTime} function before test
 * */
public class SortCompare {
    public static void main(String[] args) {
        int N = 10000;
        int T = 5;
        Long seed = 2020L;
        test(SortMethodEnum.SELECTION, "", N, T, seed);                  // 83 +/- 21
        test(SortMethodEnum.BUBBLE, "", N, T, seed);                     // 205 +/- 10
        test(SortMethodEnum.INSERTION, "", N, T, seed);                  // 84 +/- 4
        test(SortMethodEnum.INSERTION, "withGuard", N, T, seed);         // 95 +/- 13
        test(SortMethodEnum.INSERTION, "no exchange", N, T, seed);       // 60 +/- 29
        test(SortMethodEnum.TOP_DOWN_MERGE, "", N, T, seed);              // 3.0 +/- 1.85
        test(SortMethodEnum.BOTTOM_UP_MERGE, "", N, T, seed);             // 5.25 +/- 1.8
        test(SortMethodEnum.SHELL, "", N, T, seed);                      // 2.4 +/- 1.4
        test(SortMethodEnum.QUICK, "", N, T, seed);                      // 2.5 +/- 1.1
    }

    private static void test(SortMethodEnum alg, String method, int N, int T, Long seed){
        SortTest sortTest = new SortTest(seed, N, T);
        double[] res = sortTest.test(alg, method);
        if(method.equals("")) method = "default";
        double mean = Array.average(res);
        double std = Array.std(res);
        System.out.printf("Testing %d times on %d length.\n", T, N);
        System.out.printf("Alg. %s method %s: %.4f ms +/- %.4f ms\n", alg, method, mean, std);
    }

}
