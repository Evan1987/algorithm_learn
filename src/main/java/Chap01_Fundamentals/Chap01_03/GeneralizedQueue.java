package Chap01_Fundamentals.Chap01_03;

public class GeneralizedQueue<Item> {
    private Item[] a = (Item[]) new Object[2];
    private int N = 0;

    public boolean isEmpty(){return N == 0;}


    private void resize(int max){
        Item[] temp = (Item[]) new Object[max];
        for(int i = 0; i < this.N; i ++){
            temp[i] = this.a[i];
        }
        this.a = temp;
    }

    public void insert(Item x){
        if(this.a.length == this.N) resize(2 * this.N);
        this.a[this.N ++] = x;
    }

    public Item delete(int k){
        Item res = this.a[k];
        for(int i = k; i < this.N - 1;  i ++){
            this.a[i] = this.a[i + 1];
        }
        return res;
    }
}
