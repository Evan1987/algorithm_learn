package Chap01_Fundamentals.Chap01_05;


/**
 * 提高 Union方法的速度
 * id不再是简单保存分量id的容器，而是作为触点的连接
 * 根节点：序号与自身相同的节点
 * 最坏情况：O(N^2)
 * */
public class QuickUnionUF extends UnionFind{

    public QuickUnionUF(int N) {
        super(N);
    }

    /**
     * 复杂度：树的高度
     * */
    @Override
    public int find(int p) {
        while(p != this.id[p])
            p = this.id[p];
        return p;
    }

    /**
     * 复杂度：树的高度
     * */
    @Override
    public void union(int p, int q) {
        // 将根节点统一
        int pRoot = find(p);
        int qRoot = find(q);
        if(pRoot == qRoot) return;
        this.id[pRoot] = qRoot;
        this.count --;
    }
}


