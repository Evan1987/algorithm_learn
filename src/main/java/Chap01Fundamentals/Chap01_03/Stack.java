package Chap01Fundamentals.Chap01_03;

import java.util.Iterator;

public class Stack<Item> implements Iterator<Item> {
    private class Node{
        Item item;
        Node next;
    }
    private Node first;
    private int N;

    public boolean isEmpty(){
        return this.first == null;
    }

    public int size(){
        return this.N;
    }

    public void push(Item item){
        Node oldFirst = this.first;
        this.first = new Node();
        this.first.item = item;
        this.first.next = oldFirst;
    }

}
