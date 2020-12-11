package misc.ahocorasick.interval;

import java.util.ArrayList;
import java.util.List;

/**
 * 线段树上的节点
 * @author zhaochengming
 * @date 2020/12/11 23:22
 */
public class IntervalNode {

    // 方向
    private enum Direction {
        LEFT, RIGHT
    }

    private IntervalNode left = null;
    private IntervalNode right = null;
    // 中点
    private final int point;
    // 区间集合
    private final List<IInterval> intervals = new ArrayList<>();

    public IntervalNode(List<IInterval> intervals) {
        this.point = getMedian(intervals);
        // 中点为界左边的区间
        List<IInterval> toLeft = new ArrayList<>();
        // 中点为界右边的区间
        List<IInterval> toRight = new ArrayList<>();

        for(IInterval interval: intervals) {
            if(interval.getEnd() < this.point)
                toLeft.add(interval);
            else if(interval.getStart() > this.point)
                toRight.add(interval);
            else
                this.intervals.add(interval);
        }

        if(toLeft.size() > 0)
            this.left = new IntervalNode(toLeft);
        if(toRight.size() > 0)
            this.right = new IntervalNode(toRight);
    }

    // 获取中点
    public int getMedian(List<IInterval> intervals) {
        int start = -1;
        int end = -1;
        for(IInterval interval: intervals) {
            int currentStart = interval.getStart();
            int currentEnd = interval.getEnd();

            // 找最左
            if(start == -1 || currentStart < start)
                start = currentStart;
            // 找最右
            if(end == -1 || currentEnd > end)
                end = currentEnd;
        }
        return (start + end) / 2;
    }

    // 寻找与interval有重叠的区间
    public List<IInterval> findOverlaps(IInterval interval) {
        List<IInterval> overlaps = new ArrayList<>();

        // 去右边找
        if(this.point < interval.getStart()) {
            addOverlaps(interval, overlaps, checkForOverlaps(interval, Direction.RIGHT));
            if(this.right != null)
                addOverlaps(interval, overlaps, this.right.findOverlaps(interval));
        }
        // 去左边找
        else if (this.point > interval.getEnd()) {
            addOverlaps(interval, overlaps, checkForOverlaps(interval, Direction.LEFT));
            if(this.left != null)
                addOverlaps(interval, overlaps, this.left.findOverlaps(interval));
        }
        // 在当前区间
        else {
            addOverlaps(interval, overlaps, this.intervals);
            if(this.right != null)
                addOverlaps(interval, overlaps, this.right.findOverlaps(interval));
            if(this.left != null)
                addOverlaps(interval, overlaps, this.left.findOverlaps(interval));
        }
        return overlaps;
    }

    /**
     * 将与目标区间重叠的区间加入到列表中
     * @param interval 目标区间
     * @param overlaps 重叠列表
     * @param newOverlaps 希望加入的新的重叠区间
     * */
    protected void addOverlaps(IInterval interval, List<IInterval> overlaps, List<IInterval> newOverlaps) {
        for(IInterval overlap: newOverlaps)
            if(!overlap.equals(interval))
                overlaps.add(overlap);
    }

    /**
     * 寻找重叠
     * @param interval 目标区间
     * @param direction 方向，表明重叠区间在interval的左边还是右边
     * */
    protected List<IInterval> checkForOverlaps(IInterval interval, Direction direction) {
        List<IInterval> overlaps = new ArrayList<>();
        for(IInterval curr: this.intervals) {
            switch (direction) {
                case LEFT:
                    if(curr.getStart() <= interval.getEnd())
                        overlaps.add(curr);
                    break;
                case RIGHT:
                    if(curr.getEnd() >= interval.getStart())
                        overlaps.add(curr);
                    break;
            }
        }
        return overlaps;
    }
}
