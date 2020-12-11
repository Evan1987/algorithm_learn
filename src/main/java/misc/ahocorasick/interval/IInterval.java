package misc.ahocorasick.interval;

/**
 * 区间接口
 * */
public interface IInterval extends Comparable<IInterval> {

    // 起点
    int getStart();

    // 终点
    int getEnd();

    // 长度
    int size();
}
