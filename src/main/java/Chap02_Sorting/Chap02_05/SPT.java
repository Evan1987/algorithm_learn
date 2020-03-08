package Chap02_Sorting.Chap02_05;

import java.util.Arrays;
import edu.princeton.cs.algs4.StdIn;

/**
 * @author Evan
 * @date 2020/3/6 10:34
 * Ex 2.5.12，使任务完成平均时间最小
 */
public class SPT {
    public static void main(String[] args) {
        int n = StdIn.readInt();
        Job[] jobs = new Job[n];
        for(int i = 0; i < n; i ++)
            jobs[i] = new Job(StdIn.readString(), StdIn.readInt());

        Arrays.sort(jobs);
        for(Job job: jobs)
            System.out.println(job);
    }
}
