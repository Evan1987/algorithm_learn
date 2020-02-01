package Chap01_Fundamentals.Chap01_02;

import utils.URandom;

/**
 * Simulate Flip a coin multi times.
 * */
public class Flips {
    public static void main(String[] args){
        //int T = Integer.parseInt(args[0]);
        int T = 1000;
        Counter heads = new Counter("heads");
        Counter tails = new Counter("tails");
        URandom rng = new URandom(0L);
        for(int i = 0; i < T; i++){
            if(rng.nextDouble() < 0.5) heads.increment();
            else tails.increment();
        }

        System.out.println(heads);
        System.out.println(tails);

        int diff = Math.abs(heads.tally() - tails.tally());
        System.out.println("Delta: " + diff);
    }
}
