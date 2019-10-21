package Chap01Fundamentals.Chap01_03;

public class Ex19 {
    public static void main(String[] args) {
        final int[] inputs = {4, 3, 2, 1};
        SingleLinkedList<Integer> list = new SingleLinkedList<Integer>(){{
            for(int x: inputs){addHead(x);}
        }};
        System.out.println(list); // 1 -> 2 -> 3 -> 4 ->

        list.deleteLastNode();
        System.out.println(list);  // 1 -> 2 -> 3 ->
    }
}
