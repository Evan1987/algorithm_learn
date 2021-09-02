package algo4.chap01_fundamentals.Chap01_05;

/**
 * 高度加权，总是将矮树接到高树上
 * Added by Ex14
 * */
public class HeightWeightedQuickUnionUF extends QuickUnionUF{
    private int[] hz;

    public HeightWeightedQuickUnionUF(int N) {
        super(N);
        this.hz = new int[N];  // 初始高度为0
    }

    @Override
    public void union(int p, int q) {
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



