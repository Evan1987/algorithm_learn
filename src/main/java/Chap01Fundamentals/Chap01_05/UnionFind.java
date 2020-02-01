package Chap01Fundamentals.Chap01_05;


abstract class UnionFind {
    int[] id;  // 存放某种分量信息，不一定是分量id
    int count;  // 分量数量

    /**
     * @param N: 初始触点的数量
     * */
    UnionFind(int N){
        this.count = N;
        this.id = new int[N];
        for(int i = 0; i < N; i ++)
            this.id[i] = i;  // 每个触点单独成为分量
    }

    /**
     * 给定触点返回其所在的分量id
     * */
    abstract int find(int p);

    /**
     * 联通两个触点
     * */
    abstract void union(int p, int q);

    boolean connected(int p, int q){
        return find(p) == find(q);
    }
}


/**
 * Find方法很快，但是 union效率低
 * id: 一一对应，每个触点对应其分量id
 * */
class QuickFindUF extends UnionFind {

    QuickFindUF(int N) {
        super(N);
    }

    @Override
    int find(int p) {
        return this.id[p];  // very fast
    }

    @Override
    void union(int p, int q) {
        int pID = find(p);  // +1
        int qID = find(q);  // +1
        if(pID == qID) return;

        // 更改p所属分量下全部触点的分量id为q分量的id
        for(int i = 0; i < this.id.length; i ++)
            if(this.id[i] == pID) this.id[i] = qID;  // (N + 1) 只有一个true or (2N - 1) 只有一个false
        this.count --;  // 每次连接，都会少一个分量
    }
}


/**
 * 提高 Union方法的速度
 * id不再是简单保存分量id的容器，而是作为触点的连接
 * 根节点：序号与自身相同的节点
 * 最坏情况：O(N^2)
 * */
class QuickUnionUF extends UnionFind{

    QuickUnionUF(int N) {
        super(N);
    }

    /**
     * 复杂度：树的高度
     * */
    @Override
    int find(int p) {
        while(p != this.id[p])
            p = this.id[p];
        return p;
    }

    /**
     * 复杂度：树的高度
     * */
    @Override
    void union(int p, int q) {
        // 将根节点统一
        int pRoot = find(p);
        int qRoot = find(q);
        if(pRoot == qRoot) return;
        this.id[pRoot] = qRoot;
        this.count --;
    }
}

/**
 * 解决连接问题中的倾斜，总是选择将小树连接到大树上
 * 需要额外的数据记录每个分量的大小
 * */
class WeightedQuickUnionUF extends QuickUnionUF {
    private int[] sz;  // 各个分量所含触点的数量

    WeightedQuickUnionUF(int N) {
        super(N);
        this.sz = new int[N];
        for(int i = 0; i < N; i ++)
            this.sz[i] = 1;
    }

    /**
     * lgN
     * */
    @Override
    void union(int p, int q) {
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

/**
 * 路径压缩 Added by Ex12
 * */
class PathCompressedQuickUnion extends QuickUnionUF {

    PathCompressedQuickUnion(int N) {
        super(N);
    }

    @Override
    int find(int p) {
        int temp = p;

        // 获取root
        while(p != this.id[p])
            p = this.id[p];
        int root = p;

        // 再寻一次，将路径上的所有触点都改为root
        p = temp;
        while(root != this.id[p]){
            temp = this.id[p];
            this.id[p] = root;
            p = temp;
        }

        return root;
    }
}

/**
 * 高度加权，总是将矮树接到高树上
 * Added by Ex14
 * */
class HeightWeightedQuickUnionUF extends QuickUnionUF{
    private int[] hz;

    HeightWeightedQuickUnionUF(int N) {
        super(N);
        this.hz = new int[N];  // 初始高度为0
    }

    @Override
    void union(int p, int q) {
        int pRoot = find(p);
        int qRoot = find(q);
        if(pRoot == qRoot) return;
        if(this.hz[pRoot] < this.hz[qRoot]){  // p比q矮
            this.id[pRoot] = qRoot;
        }else if(this.hz[pRoot] > this.hz[qRoot]){
            this.id[qRoot] = pRoot;
        }else{
            this.id[pRoot] = qRoot;
            this.hz[qRoot] ++;
        }

        this.count --;
    }
}



class Test {
    private static int[][] inputs = {
            {4, 3}, {3, 8}, {6, 5}, {9, 4}, {2, 1},
            {5, 0}, {7, 2}, {6, 1}, {1, 0}, {6, 7}
    };
    public static void main(String[] args) {
        //QuickFindUF uf = new QuickFindUF(10);
        WeightedQuickUnionUF uf = new WeightedQuickUnionUF(10);
        for(int[] pair: inputs){
            int p = pair[0];
            int q = pair[1];
            if(uf.connected(p, q)) continue;
            uf.union(p, q);
            System.out.println(p + " " + q);
        }
    }
}