package Chap01_Fundamentals.Chap01_02;

public class Counter implements Comparable<Counter>{
    private final String name;  // The counter's name
    private int count = 0;  //current value

    public Counter(String id){
        this.name = id;
    }

    public void increment(){
        this.count ++;
    }

    public int tally(){
        return this.count;
    }

    @Override
    public String toString() {
        return this.name + " " + this.count;
    }

    public int compareTo(Counter that) {
        if(this.count < that.count) return -1;
        if(this.count > that.count) return +1;
        return 0;
    }
}