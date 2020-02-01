package Chap01_Fundamentals.Chap01_04;

import utils.Tuple;

/**
 * 变冷还是变热， 1~N中的秘密数字
 * ~2lgN 或 ~lgN
 * */
public class Ex34 {
    public static void main(String[] args) {
        int x = 43;
        int lo = 1;
        int[] Ns = {100, 500, 1000, 2000};
        Secret secret = new Secret(x);
        for(int N: Ns){
            System.out.println("The total num: " + N);
            int guessedSecret1 = GuessMethod.guess1(secret, lo, N);
            System.out.println("Method1 secret is " + guessedSecret1);
            System.out.println("Method1 Guess num: " + secret.guessCount);

            secret.reset();
            int guessedSecret2 = GuessMethod.guess2(secret, lo, N);
            System.out.println("Method2 secret is " + guessedSecret1);
            System.out.println("Method2 Guess num: " + secret.guessCount + "\n");
        }
    }
}

enum Tag {

    HOTTER(true),
    COOLER(false);

    private boolean isCloser;
    Tag(boolean isCloser){
        this.isCloser = isCloser;
    }

}

class Secret {
    private int x;
    private int lastDistance = -1;
    public int guessCount;

    public Secret(int x){
        this.x = x;
        this.guessCount = 0;
    }

    public void reset(){
        this.guessCount = 0;
    }

    public Tuple<Boolean, Tag> guess(int key){

        this.guessCount += 1;
        int distance = Math.abs(key - this.x);
        if(lastDistance < 0){
            lastDistance = distance;
            return new Tuple<>(distance == 0, null);
        }

        int oldLastDistance = lastDistance;
        lastDistance = distance;
        return new Tuple<>(distance == 0,
                distance < oldLastDistance ? Tag.HOTTER : Tag.COOLER);
    }
}

class GuessMethod {
    /**
     * ~2lgN，每次区间减半需要猜 2次，分别是区间的两个端点
     */
    static int guess1(Secret secret, int lo, int hi){
        while(lo < hi){
            Tuple<Boolean, Tag> res1 = secret.guess(lo);
            if(res1._1) return lo;
            Tuple<Boolean, Tag> res2 = secret.guess(hi);
            if(res2._1) return hi;
            if(res2._2 == Tag.HOTTER){  // secret on the right half
                lo = (lo + hi) / 2;
                hi --;  // move one step because `hi` is not the secret
            }else{  // secret on the left half
                hi = (lo + hi) / 2;
                lo ++;  // move one step because `lo` is not the secret
            }
        }
        return lo;
    }

    /**
     * ~lgN，每次区间减半只需 1次，guess的数字超过边界， -N~2N之间，不断移动边界使其夹逼至只有 1个元素
     * */
    static int guess2(Secret secret, int lo, int hi){
        int mid;
        int guessKey = lo;
        Tuple<Boolean, Tag> res = secret.guess(lo);
        if(res._1) return guessKey;
        while(lo < hi){
            mid = (lo + hi) / 2;
            // keep the two guesses have the same mid point with secret interval
            guessKey = lo + hi - guessKey;
            res = secret.guess(guessKey);
            if(res._1) return guessKey;
            if(res._2 == Tag.COOLER){  // adjust the nearest boundary to the guessKey
                if(lo == nearBoundary(guessKey, lo, hi))
                    // Due to the floor division, mid may be same as lo, add +1 when happens
                    lo = (lo == mid ? lo + 1 : mid);
                else
                    hi = mid;
            }else{      // Hotter, adjust the farthest boundary to the guessKey
                if(lo == farBoundary(guessKey, lo, hi))
                    lo = (lo == mid ? lo + 1: mid);
                else
                    hi = mid;
            }
        }
        return lo;
    }
    private static int nearBoundary(int key, int lo, int hi){
        return Math.abs(key - lo) < Math.abs(key - hi) ? lo : hi;
    }

    private static int farBoundary(int key, int lo, int hi){
        return Math.abs(key - lo) > Math.abs(key - hi) ? lo : hi;
    }
}
