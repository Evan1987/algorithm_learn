package Chap01Fundamentals.Chap01_03;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * LIFO(Last In First Out) Stack -> Stack
 * */
public class Stack<Item> implements Iterable<Item> {
    private Node<Item> first;
    private int N;

    public boolean isEmpty(){
        return this.first == null;
    }

    public int size(){
        return this.N;
    }

    public void push(Item item){
        Node oldFirst = this.first;
        this.first = new Node<Item>(item);
        this.first.item = item;
        this.first.next = oldFirst;
        this.N ++;
    }

    public Item pop(){
        Item item = this.first.item;
        this.first = this.first.next;
        this.N -- ;
        return item;
    }

    public Stack(){
        this.first = null;
        this.N = 0;
    }

    /**
     * Added by Ex 1.3.42
     * */
    public Stack(Stack<Item> s){
        this.first = new Node<Item>(s.first);
        this.N = s.N;
    }

    /**
     * Added by Ex 1.3.7
     * */
    public Item peek(){
        if(isEmpty()) throw new NoSuchElementException("Stack is empty!");
        return this.first.item;
    }

    private class ListIterator implements Iterator<Item> {
        private Node<Item> current = first;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove() {
        }
    }

    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    public static void main(String[] args){
        String[] inputs = {"to", "be", "or", "not", "to", "-", "be", "-", "-", "that", "-", "-", "-", "is"};
        Stack<String> s = new Stack<String>();
        for(String x: inputs){
            if(!x.equals("-")){
                s.push(x);
            }else{
                System.out.println(s.pop());
            }
        }
        System.out.println("(" + s.size() + " left on stack)");
    }

}
