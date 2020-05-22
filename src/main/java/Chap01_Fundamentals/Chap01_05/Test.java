package Chap01_Fundamentals.Chap01_05;


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