package BaseLearn;

public class LoopTest {
    public static void main(String[] args) {
        int[] numbers = {10, 20, 30, 40, 50};
        for (int number : numbers){
            if (number == 30) break;
            System.out.println(number);
        }
    }
}
