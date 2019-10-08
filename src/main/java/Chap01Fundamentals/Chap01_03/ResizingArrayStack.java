package Chap01Fundamentals.Chap01_03;

import java.util.Iterator;

public class ResizingArrayStack<Item> {
    private Item[] a = (Item[]) new Object[1];
    private int N;
    public boolean isEmpty(){
        return this.N == 0;
    }

    public int size(){
        return this.N;
    }

    private void resize(int max){
        Item[] temp = (Item[]) new Object[max];
        for(int i = 0; i < this.N; i++){
            temp[i] = this.a[i];
        }
        this.a = temp;
    }

    public void push(Item item){
        if(this.N == this.a.length) resize(this.a.length * 2);
        this.a[this.N ++] = item;
    }

    public Item pop(){
        --this.N;
        Item item = this.a[this.N];
        this.a[this.N] = null;
        if(this.N > 0 && this.N == this.a.length / 4) resize(this.a.length / 2);
        return item;
    }

    private class ReverseArrayIterator implements Iterator<Item>{
        private int i = N;
        public boolean hasNext() {return i > 0;}
        public Item next() {return a[--i];}
        public void remove() {}
    }

    public Iterator<Item> iterator(){
        return new ReverseArrayIterator();
    }


}
