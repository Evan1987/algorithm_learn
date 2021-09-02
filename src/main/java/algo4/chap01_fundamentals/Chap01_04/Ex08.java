package algo4.chap01_fundamentals.Chap01_04;

import java.util.Arrays;

public class Ex08 {
    private static int sameCount(int[] v){
        int N = v.length;
        if(N < 2) return 0;
        Arrays.sort(v);
        int cnt = 0;
        int n = 1;
        int current = v[0];
        for(int i = 1; i < N; i++){
            if(v[i] == current){
                n ++;
            }else{
                System.out.println(current + ": " + n);
                cnt += (n * (n - 1) / 2);
                current = v[i];
                n = 1;
            }
        }

        if(n > 1){
            System.out.println(current + ": " + n);
            cnt += (n * (n - 1) / 2);
        }
        return cnt;
    }

    public static void main(String[] args) {
        int[] test = {1, 2, 3, 4, 4, 4, 3, 2, 2, 1, 1};  // 10
        System.out.println(sameCount(test));

    }
}
