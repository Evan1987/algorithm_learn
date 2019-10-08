package Chap01Fundamentals.Chap01_03;

import java.util.Iterator;

/**
 * FIFO(First In First Out) Queue -> Queue
 * */
public class Queue<Item> implements Iterable<Item> {
    private class Node {
        Item item;
        Node next;
    }

    private Node first;
    private Node last;
    private int N;

    public boolean isEmpty(){
        return first == null;
    }

    public int size(){
        return N;
    }

    public void enqueue(Item item){
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;  // always keep null;
        if(isEmpty()){
            first = last;
        }else{
            oldLast.next = last;
        }
        N ++;
    }

    public Item dequeue(){
        Item item = first.item;
        first = first.next;
        if(isEmpty()) last = null;
        N --;
        return item;
    }

    private class ListIterator implements Iterator<Item>{
        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            Item item = current.item;
            current = current.next;
            return item;
        }

        @Override
        public void remove() {}
    }

    public Iterator<Item> iterator(){
        return new ListIterator();
    }
    public static void main(String[] args){
        String[] inputs = {"to", "be", "or", "not", "to", "-", "be", "-", "-", "that", "-", "-", "-", "is"};
        Queue<String> s = new Queue<String>();
        for(String x: inputs){
            if(!x.equals("-")){
                s.enqueue(x);
            }else{
                System.out.println(s.dequeue());
            }
        }
        System.out.println("(" + s.size() + " left on stack)");
    }
}
