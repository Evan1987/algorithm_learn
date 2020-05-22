package Chap01_Fundamentals.Chap01_05;


/**
 * 解决连接问题中的倾斜，总是选择将小树连接到大树上
 * 需要额外的数据记录每个分量的大小
 * */
public class WeightedQuickUnionUF extends QuickUnionUF {
    private int[] sz;  // 各个分量所含触点的数量

    public WeightedQuickUnionUF(int N) {
        super(N);
        this.sz = new int[N];
        for(int i = 0; i < N; i ++)
            this.sz[i] = 1;
    }

    /**
     * lgN
     * */
    @Override
    public void union(int p, int q) {
        int pRoot = find(p);
        int qRoot = find(q);
        if(pRoot == qRoot) return;
        if(this.sz[pRoot] < this.sz[qRoot]){    // pRoot为小树
            this.id[pRoot] = qRoot;             // 将小树连接至大树
            this.sz[qRoot] += this.sz[pRoot];  // 大树分量大小增加
        }else{
            this.id[qRoot] = pRoot;
            this.sz[pRoot] += this.sz[qRoot];
        }
        this.count --;
    }
}


