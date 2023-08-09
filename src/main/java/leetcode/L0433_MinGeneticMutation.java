package leetcode;

import java.util.*;

/**
 * @author : zhaochengming
 * @date : 2023/8/7 19:06
 * @description : https://leetcode.cn/problems/minimum-genetic-mutation/
 * 最小基因变化，每一次变化要保证基因在库里
 */
public class L0433_MinGeneticMutation {
    // 广度优先，将变化的搜索域减小到bank范围内，即对bank进行预处理
    static class Solution {

        private static class AutoHandler {

            private List<Integer>[] adj;
            private String[] bank;

            public AutoHandler(String[] bank) {
                this.bank = bank;
                int n = bank.length;
                int m = bank[0].length();   // 基因的长度

                // 初始化邻接表
                adj = new List[n];
                for (int i = 0; i < n; i ++) {
                    adj[i] = new LinkedList<>();
                }

                for (int i = 0; i < n; i ++) {
                    String a = bank[i];
                    for (int j = i + 1; j < n; j ++) {
                        String b = bank[j];
                        int diff = 0;
                        for (int k = 0; k < m; k ++) {
                            if (a.charAt(k) != b.charAt(k)) {
                                diff ++;
                                if (diff > 1) break;
                            }
                        }
                        // 相差只有一个字符，加入到邻接表里
                        if (diff == 1) {
                            adj[i].add(j);
                            adj[j].add(i);
                        }
                    }
                }
            }

            private int indexOf(String a) {
                if (a == null || a.isEmpty()) return -1;
                for (int i = 0; i < bank.length; i ++) {
                    if (bank[i].equals(a)) return i;
                }
                return -1;

            }

            public int findPath(String start, String end) {
                int endIndex = indexOf(end);
                if (endIndex < 0) return -1;

                int n = this.bank.length;
                int m = this.bank[0].length();

                Deque<Integer> q = new LinkedList<>();
                boolean[] visited = new boolean[n];
                int step = 1;
                for (int i = 0; i < n; i ++) {
                    int diff = 0;
                    for (int k = 0; k < m; k ++) {
                        if (start.charAt(k) != bank[i].charAt(k)) {
                            diff ++;
                            if (diff > 1) break;
                        }
                    }
                    if (diff == 1) {
                        q.offer(i);
                        visited[i] = true;
                    }
                }

                while (!q.isEmpty()) {
                    int size = q.size();
                    for (int i = 0; i < size; i ++) {
                        int candidate = q.poll();
                        if (candidate == endIndex) return step;

                        for (int index: adj[candidate]) {
                            if (visited[index]) continue;
                            q.offer(index);
                            visited[index] = true;
                        }
                    }
                    step ++;
                }

                return -1;
            }
        }


        public int minMutation(String startGene, String endGene, String[] bank) {
            if (bank == null || bank.length == 0) return -1;
            AutoHandler handler = new AutoHandler(bank);
            return handler.findPath(startGene, endGene);
        }
    }


    // 广度优先 实际是暴力搜索
    static class Solution1 {
        public int minMutation(String startGene, String endGene, String[] bank) {
            Set<String> geneSet = new HashSet<>(Arrays.asList(bank));
            if (!geneSet.contains(endGene)) return -1;
            if (startGene.equals(endGene)) return 0;

            int m = startGene.length();
            Set<String> visited = new HashSet<>();
            char[] keys = {'A', 'C', 'G', 'T'};

            Deque<String> q = new LinkedList<>();
            q.offer(startGene);
            visited.add(startGene);
            int step = 1;
            while (!q.isEmpty()) {
                // 每次都把同一层poll出来
                int size = q.size();
                for (int i = 0; i < size; i ++) {
                    String target = q.poll();
                    for (int j = 0; j < m; j ++) {
                        char curr = target.charAt(j);
                        for (char c: keys) {
                            if (curr == c) continue;
                            StringBuilder sb = new StringBuilder(target);
                            sb.setCharAt(j, c);
                            String next = sb.toString();

                            // 已经变化过了或者基因库里不含有该基因，则跳过
                            if (!visited.add(next) || !geneSet.contains(next)) continue;
                            if (next.equals(endGene)) return step;
                            q.offer(next);
                        }
                    }
                }
                step ++;
            }

            return -1;
        }
    }
}
