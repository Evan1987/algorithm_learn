package Chap01Fundamentals.Chap01_01;

import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;
import java.util.Random;

public class Ex36 {
    private static void shuffle(int[] a){
        Random rng = new Random();
        int N = a.length;
        for(int i = 0; i < N; i++){
            // switch a[i] with a[i...N-1] randomly
            int r = i + rng.nextInt(N - i);
            int temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    public static void main(String[] args){
        if(args.length < 2) throw new ValueException("The args num expected to be 2.");
        int M = Integer.parseInt(args[0]);
        int N = Integer.parseInt(args[1]);
        int[] a = new int[M];
        int[][] log = new int[M][M];
        for(int i = 0; i < N; i++){
            for(int j = 0; j < M; j++){
                a[j] = j;
            }
            shuffle(a);
            for(int j = 0; j < M; j++){
                log[a[j]][j] += 1;
            }
        }

        for(int i = 0; i < M; i++){
            for(int j = 0; j < M; j++){
                System.out.print(log[i][j] + " ");
            }
            System.out.println();
        }
    }
}

