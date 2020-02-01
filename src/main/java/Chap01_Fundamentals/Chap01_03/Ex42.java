package Chap01_Fundamentals.Chap01_03;

public class Ex42 {
    public static void main(String[] args) {
        Stack<Integer> s1 = new Stack<Integer>();
        s1.push(1);
        s1.push(3);

        Stack<Integer> s2 = new Stack<Integer>(s1);
        System.out.println(s2.size());

        s2.push(5);
        System.out.println(s2.size());
        System.out.println(s1.size());
    }
}
