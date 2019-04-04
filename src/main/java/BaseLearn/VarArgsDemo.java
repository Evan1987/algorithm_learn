package BaseLearn;

public class VarArgsDemo {
    public static void printMax(double... numbers){
        if(numbers.length == 0) {
            System.out.println("There is no args passed!");
            return;
        }

        double result = Double.NEGATIVE_INFINITY;
        for (double num: numbers){
            result = num > result ? num : result;
        }
        System.out.println(result);
    }

    public static void main(String[] args){
        printMax(1.0, 2.1, 3.5);
    }
}
