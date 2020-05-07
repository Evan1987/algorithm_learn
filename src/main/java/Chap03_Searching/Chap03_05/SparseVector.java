package Chap03_Searching.Chap03_05;


import java.util.Map;
import java.util.TreeMap;

/**
 * @author Evan
 * @date 2020/5/7 18:13
 */
public class SparseVector {
    private int d;                      // dimension
    private Map<Integer, Double> st;    // the index-value pairs in sparse vector

    public SparseVector(int d){
        this.d = d;
        this.st = new TreeMap<>();
    }

    public SparseVector(int d, Map<Integer, Double> st){
        this.d = d;
        this.st = new TreeMap<>(st);
    }

    public SparseVector(SparseVector sv){
        this(sv.d, sv.st);
    }

    private void indexCheck(int i){
        if(i < 0 || i >= this.d) throw new IllegalArgumentException("`i` is beyond the index of array");
    }

    public void put(int i, double value){
        this.indexCheck(i);
        if(value == 0.0) this.st.remove(i);
        else this.st.put(i, value);
    }

    public double get(int i){
        this.indexCheck(i);
        return this.st.getOrDefault(i, 0.0);
    }

    // num of non-zero indexes in vector
    public int nnz(){
        return this.st.size();
    }

    public int length(){
        return this.d;
    }

    // Dot product with another sparse vector
    public double dot(SparseVector that){
        if(this.length() != that.length()) throw new IllegalArgumentException("Unequal lengths");
        double sum = 0.0;
        SparseVector a, b;
        if(this.nnz() <= that.nnz()){
            a = this;
            b = that;
        }
        else{
            a = that;
            b = this;
        }
        for(Map.Entry<Integer, Double> entry: a.st.entrySet())
            sum += entry.getValue() * b.get(entry.getKey());
        return sum;
    }

    // Dot product with another dense vector
    public double dot(double[] that){
        double sum = 0.0;
        for(int i = 0; i < that.length; i ++)
            sum += that[i] * this.get(i);
        return sum;
    }

    public double l2norm(){
        return Math.sqrt(this.dot(this));
    }

    // Do scalar-multiply
    public SparseVector scale(double alpha){
        SparseVector c = new SparseVector(this.d);
        for(Map.Entry<Integer, Double> entry: this.st.entrySet())
            c.st.put(entry.getKey(), entry.getValue() * alpha);
        return c;
    }

    // plus another sparse vector
    public SparseVector plus(SparseVector that){
        if(this.length() != that.length()) throw new IllegalArgumentException("Unequal lengths");
        SparseVector c = new SparseVector(this);
        for(Map.Entry<Integer, Double> entry: that.st.entrySet())
            c.put(entry.getKey(), c.get(entry.getKey()) + entry.getValue());
        return c;
    }

    @Override
    public String toString(){
        return this.st.toString();
    }

    public static void main(String[] args) {
        SparseVector a = new SparseVector(10);
        SparseVector b = new SparseVector(10);
        a.put(3, 0.50);
        a.put(9, 0.75);
        a.put(6, 0.11);
        a.put(6, 0.00);
        b.put(3, 0.60);
        b.put(4, 0.90);
        System.out.println("a = " + a);     // {3: 0.5, 9: 0.75}
        System.out.println("b = " + b);     // {3: 0.6, 4: 0.9}
        System.out.println("a dot b = " + a.dot(b));        // 0.3
        System.out.println("a + b   = " + a.plus(b));       // {3: 1.1, 4: 0.9, 9: 0.75}
    }
}
