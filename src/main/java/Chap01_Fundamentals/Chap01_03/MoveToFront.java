package Chap01_Fundamentals.Chap01_03;

public class MoveToFront {
    public static void main(String[] args) {
        String[] inputs = {"a", "b", "c", "a", "b", "c"};
        List<String> x = new List<String>();
        for(String input: inputs){
            x.add(input);
            System.out.println(x);
        }

//        a
//        b -> a
//        c -> b -> a
//        a -> c -> b
//        b -> a -> c
//        c -> b -> a
    }
}

class List<Item> {
    private Node<Item> head;
    private int size;

    public boolean isEmpty(){return size == 0;}

    public void add(Item item){
        Node<Item> newNode = new Node<Item>(item);

        if(isEmpty()){
            head = newNode;
            size ++;
            return;
        }

        if(head.item == item){
            head = head.next;
            size --;
        }else{
            for(Node node = head; node != null; node = node.next){
                if(node.next != null && node.next.item == item){
                    node.next = node.next.next;
                    break;
                }
            }
        }

        newNode.next = head;
        head = newNode;
        size ++;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        for(Node x = this.head; x != null; x = x.next)
            if(res.length() == 0 ){
                res.append(x.item);
            }else{
                res.append(" -> ").append(x.item);
            }
        return res.toString();
    }
}
