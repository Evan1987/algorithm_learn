package Chap01Fundamentals.Chap01_03;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SingleLinkedList<Item>  {

    private Node<Item> first;
    private int N;

    public boolean isEmpty(){return this.first == null;}
    public int size(){return this.N;}

    public void addHead(Item item){
        Node<Item> oldFirst = this.first;
        this.first = new Node<Item>(item);
        this.first.next = oldFirst;
        this.N ++;
    }

    // Ex 19
    public void deleteLastNode(){
        if(this.N == 0) return;
        if(this.N == 1)
            this.first = null;
        else
            for(Node<Item> x = this.first; x != null; x = x.next){
                if(x.next.next == null)
                    x.next = null;
            }
        this.N --;
    }

    // Ex 20
    public void delete(int k){
        if(k > this.N) throw new IndexOutOfBoundsException("The K is out of List's bound.");
        if(k == 1){
            this.first = this.first.next;
            return;
        }
        int index = 1;
        for(Node<Item> x = this.first; x != null; x = x.next){
            if(index == k - 1) {
                x.next = x.next.next;
                break;
            }
            index ++;
        }
    }

    // Ex 21
    public boolean find(Item key){
        for(Node<Item> x = this.first; x != null; x = x.next){
            if(x.item == key) return true;
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        for(Node x = this.first; x != null; x = x.next)
            res.append(x.item).append(" -> ");
        return res.toString();
    }
}
