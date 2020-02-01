package Chap01_Fundamentals.Chap01_03;

public class Ex20 {
    public static void main(String[] args) {
        final int[] inputs = {6, 5, 4, 3, 2, 1};
        SingleLinkedList<Integer> list = new SingleLinkedList<Integer>(){{
            for(int x: inputs){addHead(x);}
        }};
        System.out.println(list); // 1 -> 2 -> 3 -> 4 -> 5 -> 6 ->

        list.delete(3);
        System.out.println(list);  // 1 -> 2 -> 4 -> 5 -> 6 ->
    }
}
