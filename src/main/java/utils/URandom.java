package utils;

import java.util.Random;

public class URandom {
    private static Random random;
    private static Long seed;
    static {
        seed = System.currentTimeMillis();
        random = new Random(seed);
    }

    public URandom(Long seed){
        URandom.seed = seed;
        random = new Random(seed);
    }

    public URandom(){}

    public Long getSeed(){
        return seed;
    }

    public double nextDouble(){
        return random.nextDouble();
    }

    public int uniform(int N){
        return random.nextInt(N);
    }

    public int uniform(int a, int b){
        return a + random.nextInt(b - a);
    }

    public double uniform(double a, double b){
        return a + random.nextDouble() * (b - a);
    }
}
