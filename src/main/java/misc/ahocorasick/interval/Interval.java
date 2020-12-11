package misc.ahocorasick.interval;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * 区间
 * @author zhaochengming
 * @date 2020/12/11 22:50
 */
public class Interval implements IInterval {
    private final int start;
    private final int end;

    public Interval(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public int getStart() {
        return this.start;
    }

    @Override
    public int getEnd() {
        return this.end;
    }

    @Override
    public int size() {
        return this.end - this.start + 1;
    }

    // 是否交叉
    public boolean overlapWith(Interval other) {
        return this.start <= other.getEnd() && this.end >= other.getStart();
    }

    // 是否覆盖
    public boolean overlapWith(int point) {
        return this.start <= point && point <= this.end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Interval)) return false;
        Interval interval = (Interval) o;
        return start == interval.start &&
                end == interval.end;
    }

    @Override
    public int hashCode() {
        return this.start % 100 + this.end % 100;
    }

    @Override
    public int compareTo(@NotNull IInterval o) {
        int cmp = this.start - o.getStart();
        return cmp != 0 ? cmp : this.end - o.getEnd();
    }

    @Override
    public String toString() {
        return this.start + ":" + this.end;
    }
}
