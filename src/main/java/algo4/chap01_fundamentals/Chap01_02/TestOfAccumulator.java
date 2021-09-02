package algo4.chap01_fundamentals.Chap01_02;

public class TestOfAccumulator {
    public static void main(String[] args){
        Accumulator accumulator = new Accumulator();
        int[] nums = {1, 2, 3, 4, 5, 6, 7, 7};
        for(int x: nums){
            accumulator.addDataValue(x);
        }

        System.out.println(accumulator);
    }
}
