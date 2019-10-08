package Chap01Fundamentals.Chap01_03;

public class FixedCapacityStack<Item> {
    private Item[] a;
    private int N;
    public FixedCapacityStack(int capacity){
        this.a = (Item[]) new Object[capacity];
    }
    public boolean isEmpty(){
        return this.N == 0;
    }

    public int size(){
        return this.N;
    }

    public void push(Item item){
        if(this.N == this.a.length) resize(2 * this.a.length);
        this.a[this.N ++] = item;
    }

    public Item pop(){
        --this.N;
        this.a[this.N] = null;  // avoid detached object
        if(this.N > 0 && this.N == this.a.length / 4) resize(this.a.length / 2);
        return this.a[this.N];
    }

    private void resize(int max){
        Item[] temp = (Item[]) new Object[max];
        for(int i = 0; i < this.N; i ++){
            temp[i] = this.a[i];
        }
        this.a = temp;
    }
}
