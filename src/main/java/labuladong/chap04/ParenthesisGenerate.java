package labuladong.chap04;

import org.apache.commons.lang3.StringUtils;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * @author : zhaochengming
 * @date : 2022/5/4 17:02
 * @description : 括号生成， n对括号的组合
 */
public class ParenthesisGenerate {

    private static final List<String> res = new LinkedList<>();

    // 可用left括号数量和 right括号数量
    private static void backTrack(int left, int right, Deque<String> track) {
        if (left == 0 && right == 0) {
            res.add(StringUtils.join(track, ""));
            return;
        }

        if (left < 0 || right < 0 || left > right) {
            return;
        }

        track.offer("(");
        backTrack(left  - 1, right, track);
        track.pollLast();

        track.offer(")");
        backTrack(left, right - 1, track);
        track.pollLast();
    }

    public static List<String> solve(int n) {
        Deque<String> track = new LinkedList<>();
        backTrack(n, n, track);
        return res;
    }

    public static void main(String[] args) {
        int n = 3;
        for (String s: solve(n)) {
            System.out.println(s);
        }
    }

}
