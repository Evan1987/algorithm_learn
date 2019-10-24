package Chap01Fundamentals.Chap01_03;

public class Node<Item> {
    Item item;
    Node<Item> next;
    public Node(Item v){
        this.item = v;
        this.next = null;
    }
}
