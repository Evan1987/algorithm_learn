package labuladong.chap01;

import java.util.*;

/**
 * @author : zhaochengming
 * @date : 2022/4/19 11:08
 * @description : 打开 4位密码锁，有死亡密码不可触碰，求打开密码锁的最少次数，初始状态 0000
 */
public class OpenLock {

    private static List<String> adjacent(String target) {
        List<String> candidates = new LinkedList<>();

        for (int i = 0; i < target.length(); i ++) {
            char[] ch = target.toCharArray();
            char x = ch[i];

            // plus 1
            if (x == '9') {
                ch[i] = '0';
            } else {
                ch[i] += 1;
            }
            candidates.add(new String(ch));

            // recover
            ch[i] = x;

            // minus 1
            if (x == '0') {
                ch[i] = '9';
            } else {
                ch[i] -= 1;
            }
            candidates.add(new String(ch));
        }
        return candidates;
    }

    // 单向 BFS
    public static int solve(String[] deadEnds, String target) {
        List<String> deads = Arrays.asList(deadEnds);
        Set<String> visited = new HashSet<>();
        Queue<String> q = new LinkedList<>();

        int step = 0;
        q.offer("0000");
        visited.add("0000");
        while (!q.isEmpty()) {
            int size = q.size();   // 上一层添加的候选数量
            for (int i = 0; i < size; i ++) {
                String curr = q.poll();

                 if (curr.equals(target)) {
                     return step;
                 }
                 if (deads.contains(curr)) {
                     continue;
                 }

                 for (String candidate: adjacent(curr)) {
                     if (visited.add(candidate)) {
                         q.offer(candidate);
                     }
                 }
            }
            step ++;
        }
        return -1;
    }

    // 双向BFS
    public static int solve2(String[] deadEnds, String target) {
        List<String> deads = Arrays.asList(deadEnds);
        Set<String> visited = new HashSet<>();
        Set<String> q1 = new HashSet<>();       // 初始位置遍历
        Set<String> q2 = new HashSet<>();       // 结束位置遍历
        q1.add("0000");
        q2.add(target);
        visited.add("0000");
        visited.add(target);

        int step = 0;
        while (!q1.isEmpty() && !q2.isEmpty()) {

            // 保障每次遍历数量最小的队列
            if (q1.size() > q2.size()) {
                Set<String> tempForSwap = q2;
                q2 = q1;
                q1 = tempForSwap;
            }

            Set<String> temp = new HashSet<>();  // 代表下一次需要遍历的节点

            // 遍历q1的所有节点
            for (String curr: q1) {
                if (deads.contains(curr)) {
                    continue;
                }

                if (q2.contains(curr)) {
                    return step;
                }

                visited.add(curr);
                for (String candidate: adjacent(curr)) {
                    // ！！此处不能用 add，因为q1,q2交替使用且visited是公用的。如果用了add，当一方访问到某节点并加入visit后，下一次交替时就不会再产生该节点了

                    if (!visited.contains(candidate)){
                        temp.add(candidate);
                    }
                }
            }
            step ++;
            // 交换 q1和q2，保障双向
            q1 = q2;
            q2 = temp;
        }
        return -1;
    }

    public static void main(String[] args) {

        String[] deadEnds1 = {"8887", "8889", "8878", "8898", "8788", "8988", "7888", "9888"};
        String target1 = "8888";
        System.out.println(solve(deadEnds1, target1));
        System.out.println(solve2(deadEnds1, target1));

        System.out.println("-------");

        String[] deadEnds2 = {"1234", "5678"};
        String target2 = "1235";
        System.out.println(solve(deadEnds2, target2));
        System.out.println(solve2(deadEnds2, target2));
    }
}
