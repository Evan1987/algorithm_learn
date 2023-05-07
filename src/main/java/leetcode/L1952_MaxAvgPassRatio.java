package leetcode;

import java.util.PriorityQueue;

/**
 * @author : zhaochengming
 * @date : 2023/5/7 11:04
 * @description : https://leetcode.cn/problems/maximum-average-pass-ratio/
 * 最大平均通过率
 */
public class L1952_MaxAvgPassRatio {
    static class Solution {

        static class Info {
            int pass;
            int total;

            public Info(int pass, int total) {
                this.pass = pass;
                this.total = total;
            }

            double getPassRatio() {
                return pass * 1.0 / total;
            }

            void add(int num) {
                this.pass += num;
                this.total += num;
            }

            double delta() {
                return (this.total - this.pass) * 1.0 / (this.total * (this.total + 1));
            }
        }

        public double maxAverageRatio(int[][] classes, int extraStudents) {
            int n = classes.length;
            if (n == 1) {
                int pass = classes[0][0], total = classes[0][1];
                return (pass + extraStudents) * 1.0 / (total + extraStudents);
            }

            PriorityQueue<Info> pq = new PriorityQueue<>(n, (o1, o2) -> -Double.compare(o1.delta(), o2.delta()));
            for (int[] c: classes) {
                pq.offer(new Info(c[0], c[1]));
            }
            while (extraStudents > 0 && !pq.isEmpty()) {
                Info info = pq.poll();
                Info top = pq.peek();
                while (extraStudents > 0 && info.delta() >= top.delta()) {
                    info.add(1);
                    extraStudents --;
                }
                pq.offer(info);
            }

            double ratios = 0.;
            for (Info info: pq) {
                ratios += info.getPassRatio();
            }

            return ratios / n;
        }
    }

    public static void main(String[] args) {
        int[][] classes = {{1, 2}, {3, 5}, {2, 2}};
        int extraStudents = 2;
        Solution sol = new Solution();
        System.out.println(sol.maxAverageRatio(classes, extraStudents));
    }
}
