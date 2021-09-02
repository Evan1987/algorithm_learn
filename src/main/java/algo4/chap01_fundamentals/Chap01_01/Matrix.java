package algo4.chap01_fundamentals.Chap01_01;

public class Matrix {
    public static double dot(double[] x, double[] y){
        assert x.length == y.length: "The length doesn't match.";
        double res = 0.;
        for(int i = 0; i < x.length; i++){
            res += x[i] * y[i];
        }
        return res;
    }

    public static double[][] mult(double[][] a, double[][] b){
        int m = a.length;
        int d = a[0].length;
        int n = b[0].length;
        assert b.length == d: "The matrix's shape doesn't match.";
        double[][] res = new double[m][n];
        for(int i = 0; i < m; i++){
            for(int j = 0 ; j < n; j++){
                for(int k = 0; k < d; k++){
                    res[i][j] = a[i][k] * b[k][j];
                }
            }
        }
        return res;
    }

    public static double[][] transpose(double[][] a){
        int m = a.length;
        int n = a[0].length;
        double[][] res = new double[n][m];
        for(int i = 0; i < m; i++){
            for(int j = 0; j < n; j++){
                res[i][j] = a[j][i];
            }
        }
        return res;
    }

    public static double[] mult(double[][] a, double[] x){
        int m = a.length;
        int d = a[0].length;
        assert x.length == d: "The shape doesn't match.";
        double[] res = new double[m];
        for(int i = 0; i < m; i++){
            for(int j = 0; j < d; j++){
                res[i] += a[i][j] * x[j];
            }
        }
        return res;
    }

    public static double[] mult(double[] y, double[][] a){
        int d = y.length;
        int n = a[0].length;
        assert a.length == d: "The shape doesn't match";
        double[] res = new double[n];
        for(int i = 0; i < n; i++){
            for(int j = 0; j < d; j++){
                res[i] += y[j] * a[j][i];
            }
        }
        return res;
    }
}