package Chap01_Fundamentals.Chap01_04;

/**
 * 扔鸡蛋，不限制鸡蛋， 以 ~2lgF 实验次数检测 F
 * 以指数级累加寻找，找到 F所在的 (2^k, 2^(k+1))区间，然后再用二分法寻找 F
 * */
public class Ex24 {
    public static void main(String[] args) {
        int key = 28;
        int[] floorNums = {100, 500, 1000, 2000};
        MultiEggTest multiEggTest = new MultiEggTest();
        for(int floorNum: floorNums){
            Egg egg = new Egg(key);
            int searchKey = multiEggTest.test(floorNum, egg);
            System.out.println("Total Floor Num = " + floorNum);
            System.out.println("searchKey = " + searchKey);
            System.out.println("Test num = " + egg.dropNum + "\n");
        }

    }

}

class Egg {
    private int key;
    public int dropNum;
    public Egg(int key){
        this.key = key;
        this.dropNum = 0;
    }

    public boolean drop(int floor){
        this.dropNum += 1;
        return floor >= this.key;
    }

    public void reset(){
        this.dropNum = 0;
    }
}

class MultiEggTest {
    public int test(int floorNum, Egg egg){
        int nowFloor = 0;
        int step = 1;
        boolean isBroken = false;
        int biggestFloor = nowFloor;  // log the highest floor that the egg is not broken
        while(!isBroken & nowFloor <= floorNum){
            biggestFloor = nowFloor;
            nowFloor += step;
            step *= 2;  // arise with exponential step to find the broken shift with egg.
            isBroken = egg.drop(nowFloor);
        }
        if(!isBroken) return -1;  // The egg will not be broken even from the top floor.

        // do searching between biggestFloor and nowFloor with binary-search
        int lo = biggestFloor;
        int hi = nowFloor;
        while(lo < hi){
            int mid = lo + (hi - lo) / 2;
            if(egg.drop(mid)){  // egg broken at mid
                hi = mid;
            }else{  // egg not broken at mid
                lo = mid + 1;
            }
        }

        return lo;
    }
}

