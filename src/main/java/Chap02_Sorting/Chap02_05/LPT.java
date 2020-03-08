package Chap02_Sorting.Chap02_05;

import Chap02_Sorting.Chap02_04.MinPQ;

import java.util.Arrays;

/**
 * @author Evan
 * @date 2020/3/6 10:43
 *Ex 2.5.13，所有任务分配给 M个处理器并使得所有任务完成总时间最小
 */
public class LPT {
    public static void main(String[] args) {
        Job[] jobs = generateJobs();
        Arrays.sort(jobs);
        int M = 3;
        MinPQ<Processor> processorMinPQ = new MinPQ<>(M);
        for(int i = 0; i < M; i ++)
            processorMinPQ.push(new Processor());

        for(Job job: jobs){
            Processor targetProcessor = processorMinPQ.pop();
            targetProcessor.addJob(job);
            processorMinPQ.push(targetProcessor);
        }

        int i = 0;
        for(Processor processor: processorMinPQ){
            System.out.println("Processor " + (i ++) + ": Total time - " + processor.getTotalTime());
            for(Job job: processor.getJobs()){
                System.out.print(job + " ");
            }
            System.out.println("**************");
        }
    }

    private static Job[] generateJobs(){
        int N = 7;
        String[] jobNames = {"a", "b", "c", "d", "e", "f", "g"};
        int[] times = {2, 3, 1, 4, 1, 5, 3};
        Job[] jobs = new Job[N];
        for(int i = 0; i < N; i ++)
            jobs[i] = new Job(jobNames[i], times[i]);
        return jobs;
    }
}
