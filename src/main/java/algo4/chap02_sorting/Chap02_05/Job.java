package algo4.chap02_sorting.Chap02_05;

import org.jetbrains.annotations.NotNull;

/**
 * @author Evan
 * @date 2020/3/7 10:47
 */
public class Job implements Comparable<Job>{
    private String name;
    private int time;
    Job(String name, int time){
        this.name = name;
        this.time = time;
    }

    public int getTime() {
        return time;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "<Job " + this.name + "> " + this.time;
    }

    @Override
    public int compareTo(@NotNull Job that) {
        return Integer.compare(this.time, that.time);
    }
}
