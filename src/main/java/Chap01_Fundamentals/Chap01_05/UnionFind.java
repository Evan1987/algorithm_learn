package Chap01_Fundamentals.Chap01_05;

/**
 * @author Evan
 * @date 2020/5/22 14:33
 */
public abstract class UnionFind {
    int[] id;  // 存放某种分量信息，不一定是分量id
    int count;  // 分量数量

    /**
     * @param N: 初始触点的数量
     * */
    public UnionFind(int N){
        this.count = N;
        this.id = new int[N];
        for(int i = 0; i < N; i ++)
            this.id[i] = i;  // 每个触点单独成为分量
    }

    /**
     * 给定触点返回其所在的分量id
     * */
    public abstract int find(int p);

    /**
     * 联通两个触点
     * */
    public abstract void union(int p, int q);

    public boolean connected(int p, int q){
        return find(p) == find(q);
    }
}
