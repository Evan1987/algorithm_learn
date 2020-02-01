package Chap01_Fundamentals.Chap01_04;


/**
 * 扔鸡蛋，只有两个鸡蛋。以 ~2 * sqrt(N) 或 ~3 * sqrt(F) 检测 F
 * */
public class Ex25 {
    public static void main(String[] args) {
        int key = 24;
        int[] floorNums = {100, 500, 1000, 2000};
        TwoEggTest twoEggTest = new TwoEggTest();
        for(int floorNum: floorNums){
            Egg egg = new Egg(key);
            int searchKey = twoEggTest.test1(floorNum, egg);
            System.out.println("Total Floor Num = " + floorNum);

            System.out.println("Test1 searchKey = " + searchKey);
            System.out.println("Test1 num = " + egg.dropNum);

            egg.reset();
            searchKey = twoEggTest.test2(floorNum, egg);
            System.out.println("Test2 searchKey = " + searchKey);
            System.out.println("Test2 num = " + egg.dropNum + "\n");
        }
    }
}


class TwoEggTest {
    /**
     * ~2 * sqrt(N) 解法： 均匀步进
     * 将总楼层平均分成 m * m， m组每组m层，每次从各组最高层测试直到第一个蛋摔碎
     * 即找到 F所在的组，从该组第一层测试，直到第二个蛋摔碎，找到 F
     * */
    public int test1(int floorNum, Egg egg){
        int m = (int)Math.sqrt(floorNum);
        boolean isBroken = false;
        int nowFloor = 0;
        while(!isBroken & nowFloor <= floorNum){
            nowFloor += m;
            isBroken = egg.drop(nowFloor);
        }

        if(!isBroken) return -1;  // The egg will not be broken even from the top floor.
        int lo = nowFloor - m;
        isBroken = false;
        while(!isBroken){
            lo += 1;
            isBroken = egg.drop(lo);
        }
        return lo;
    }

    /**
     * ~3 * sqrt(F)解法:平方步进
     * (k - 1)^2 <= F <= k^2，找到 F 所在的平方数区间，然后再逐一找 F
     * */
    public int test2(int floorNum, Egg egg){
        int k = 0;
        int nowFloor = 0;
        boolean isBroken = false;
        while(!isBroken & nowFloor <= floorNum){
            k ++;
            nowFloor = k * k;
            isBroken = egg.drop(nowFloor);
        }

        if(!isBroken) return -1;

        int lo = (k - 1) * (k - 1);
        isBroken = false;
        while(!isBroken){
            lo += 1;
            isBroken = egg.drop(lo);
        }
        return lo;
    }
}