package algo4.chap02_sorting.Chap02_05;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Evan
 * @date 2020/3/7 10:46
 *
 */
public class Processor implements Comparable<Processor>{
    private List<Job> jobs;
    private int totalTime;

    public Processor(){
        this.jobs = new ArrayList<>();
        this.totalTime = 0;
    }

    public void addJob(Job job){
        this.jobs.add(job);
        this.totalTime += job.getTime();
    }

    public int getTotalTime() {
        return totalTime;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    @Override
    public int compareTo(@NotNull Processor that) {
        return Integer.compare(this.totalTime, that.totalTime);
    }
}
