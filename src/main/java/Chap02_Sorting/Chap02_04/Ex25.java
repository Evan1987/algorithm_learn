package Chap02_Sorting.Chap02_04;

import org.jetbrains.annotations.NotNull;

/**
 * @author Evan
 * @date 2020/3/2 11:24
 * CubeSum: (1) for all `a`,`b` in [0, N] and b >= a, print all a^3 + b^3 in asc order with O(N) spaces
 * (2) find all a^3 + b^3 = c^3 + d^3 combinations in [0, 10^6]
 */
public class Ex25 {
    public static void main(String[] args) {
        printCubeSum(10);

    }

    /**
     * (1) for all `a`,`b` in [0, N] and b >= a, print all a^3 + b^3 in asc order with O(N) spaces
     * */
    private static void printCubeSum(int N){
        MinPQ<CubeSum> pq = new MinPQ<>(N);
        for(int j = 0; j < N; j ++)
            pq.push(new CubeSum(0, j));  // The minimum sum for same `j`, `j` will be kept

        int count = 0;
        while(!pq.isEmpty()){
            CubeSum min = pq.pop();
            System.out.println(min.getSum() + " = " + min.getI() + "^3" + " + " + min.getJ() + "^3");
            count ++;
            if(min.getI() < min.getJ())
                pq.push(new CubeSum(min.getI() + 1, min.getJ()));
        }

        System.out.println("All printed, total counts: " + count);  // assert C(N, 2) + N
    }
}


class CubeSum implements Comparable<CubeSum> {

    private int i, j, sum;

    public CubeSum(int i, int j){
        if(j < i) throw new IllegalArgumentException("`j` should >= `i`");
        this.i = i;
        this.j = j;
        this.sum = i * i * i + j * j * j;
    }

    public int getSum(){
        return this.sum;
    }

    public int getI(){
        return this.i;
    }

    public int getJ(){
        return this.j;
    }

    @Override
    public int compareTo(@NotNull CubeSum that) {
        return Integer.compare(this.sum, that.sum);
    }
}