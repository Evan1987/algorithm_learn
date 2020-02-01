package Chap01_Fundamentals.Chap01_03;

import java.util.Iterator;

public class Bag<Item> implements Iterable<Item> {
    private class Node{
        Item item;
        Node next;
    }

    private Node first;
    private int N;

    public boolean isEmpty(){
        return first == null;
    }

    public int size(){
        return N;
    }

    public void add(Item item){
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        N ++;
    }

    private class ListIterator implements Iterator<Item> {
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
        public void remove() {

        }
    }

    public Iterator<Item> iterator(){
        return new ListIterator();
    }
}
