package algo4.chap01_fundamentals.Chap01_05;

/**
 * Find方法很快，但是 union效率低
 * id: 一一对应，每个触点对应其分量id
 * */
public class QuickFindUF extends UnionFind {

    public QuickFindUF(int N) {
        super(N);
    }

    @Override
    public int find(int p) {
        return this.id[p];  // very fast
    }

    @Override
    public void union(int p, int q) {
        int pID = find(p);  // +1
        int qID = find(q);  // +1
        if(pID == qID) return;

        // 更改p所属分量下全部触点的分量id为q分量的id
        for(int i = 0; i < this.id.length; i ++)
            if(this.id[i] == pID) this.id[i] = qID;  // (N + 1) 只有一个true or (2N - 1) 只有一个false
        this.count --;  // 每次连接，都会少一个分量
    }
}
