package leetcode;

import java.util.*;

/**
 * @author : zhaochengming
 * @date : 2022/5/21 11:14
 * @description : https://leetcode.cn/problems/evaluate-division/
 */
public class L0399_EvaluateDivision {

    static class Solution {
        public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
            int n = equations.size();
            Map<String, Integer> nodeIdMapping = new HashMap<>(2 * n);
            UnionFind uf = new UnionFind(2 * n);
            int id = 0;
            int i = 0;
            for (List<String> equation: equations) {
                String xStr = equation.get(0);
                String yStr = equation.get(1);
                Integer x = nodeIdMapping.get(xStr);
                if (x == null) {
                    x = id ++;
                    nodeIdMapping.put(xStr, x);
                }
                Integer y = nodeIdMapping.get(yStr);
                if (y == null) {
                    y = id ++;
                    nodeIdMapping.put(yStr, y);
                }

                uf.union(x, y, values[i ++]);
            }

            double[] ans = new double[queries.size()];
            int j = 0;
            for (List<String> query: queries) {
                Integer x = nodeIdMapping.get(query.get(0));
                Integer y = nodeIdMapping.get(query.get(1));
                if (x == null || y == null) {
                    ans[j] = -1.0;
                } else {
                    ans[j] = uf.isConnected(x, y);
                }
                j ++;
            }

            return ans;
        }

        private static class UnionFind {
            // 各节点对应的父节点
            private final int[] parent;

            // 各节点指向父节点的权值
            private final double[] weights;

            UnionFind(int n) {
                this.parent = new int[n];
                this.weights = new double[n];

                // 初始化，父节点都指向自己
                for (int i = 0; i < n; i ++) {
                    this.parent[i] = i;
                    this.weights[i] = 1.0;
                }
            }

            public void union(int x, int y, double v) {
                int rootX = find(x);
                int rootY = find(y);
                if (rootX == rootY) {
                    return;
                }

                parent[rootX] = rootY;
                weights[rootX] = v * weights[y] / weights[x];
            }

            // 找到根节点的 id，同时路径压缩
            public int find(int id) {
                int curr = id;
                while (curr != parent[curr]) {
                    weights[id] *= weights[parent[curr]];
                    curr = parent[curr];
                }
                parent[id] = curr;
                return curr;
            }

            public double isConnected(int x, int y) {
                int rootX = find(x);
                int rootY = find(y);
                if (rootX == rootY) {
                    return weights[x] / weights[y];
                }

                return -1.0;
            }

        }


    }


}
