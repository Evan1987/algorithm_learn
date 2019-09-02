package Chap01Fundamentals.Chap01_1;

public class Ex15 {

    private static int[] histogram(int[] a, int M){
        int[] res = new int[M];
        for (int elem:a) {
            if(elem >= 0 && elem < M){
                res[elem] ++;
            }
        }
        return res;
    }

    private static void printArray(int[] array){
        for(int elem: array){
            System.out.printf("%d\t", elem);
        }
    }

    public static void main(String[] args){
        int[] a = {1, 1, 2, 3, 1, 7, 5, 3, 2, 2};
        int[] summary = histogram(a, 8);
        printArray(summary);
    }
}
