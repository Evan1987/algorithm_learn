package Chap01Fundamentals.Chap01_03;

public class RingBuffer {
    private Node head;
    private Node tail;
    private int size;
    public RingBuffer(int size){
        this.size = size;
    }

    public boolean isEmpty(){return this.size == 0;}
    public int getSize(){return this.size;}
    public Object front(){return this.head.item;}

    public void enqueue(Object item){
        Node newNode = new Node<Object>(item);
        if(this.isEmpty()){
            head = newNode;
        }else{
            tail.next = newNode;
        }
        tail = newNode;
        this.size ++;
    }

    public Object dequeue(){
        if(this.isEmpty()){
            System.out.println("This buffer is empty.");
            return null;
        }
        Object item = head.item;
        head = head.next;
        this.size --;
        if(this.isEmpty()){this.tail = null;}  // If empty then set tail to null;
        return item;
    }
}
