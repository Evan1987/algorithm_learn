package Chap01_Fundamentals.Chap01_05;


/**
 * 路径压缩 Added by Ex12
 * */
public class PathCompressedQuickUnion extends QuickUnionUF {

    public PathCompressedQuickUnion(int N) {
        super(N);
    }

    @Override
    public int find(int p) {
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


