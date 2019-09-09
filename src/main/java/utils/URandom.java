package utils;

import java.util.Random;

public class URandom {
    private static Random random;
    private static Long seed;
    static {
        seed = System.currentTimeMillis();
        random = new Random(seed);
    }

    public URandom(){}

    public void setSeed(Long seed){this.seed = seed;}

    public static Long getSeed(){
        return seed;
    }

    public static double uniform(){
        return random.nextDouble();
    }

    public static int uniform(int N){
        return random.nextInt(N);
    }

    public static int uniform(int a, int b){
        return a + random.nextInt(b - a);
    }

    public static double uniform(double a, double b){
        return a + random.nextDouble() * (b - a);
    }
}
