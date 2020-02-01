package Chap01_Fundamentals.Chap01_04;

import java.util.Arrays;

public class Ex12 {
    public static int[] sameElements(int[] a, int[] b){
        Arrays.sort(a);
        Arrays.sort(b);
        int[] res = new int[Math.min(a.length, b.length)];
        int pa = 0, pb = 0, p = 0, Na = a.length, Nb = b.length;
        while(pa < Na && pb < Nb){
            while(pa < Na && pb < Nb && a[pa] == b[pb]){
                res[p++] = a[pa];
                pa ++;
                pb ++;
            }

            while(pa < Na && pb < Nb && a[pa] < b[pb]) pa ++;

            while(pa < Na && pb < Nb && a[pa] > b[pb]) pb ++;
        }

        return res;
    }

    public static void main(String[] args) {
        int[] a = {0, 2, 5, 6, 8, 9};
        int[] b = {0, 1, 2, 4, 6, 9};
        for(int x: sameElements(a, b)){
            System.out.println(x);
        }
    }
}
