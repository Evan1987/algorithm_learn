package algo4.chap03_searching.Chap03_02;

/**
 * @author Evan
 * @date 2020/3/10 13:09
 * 平均比较次数
 */
public class Ex07 {
    public static void main(String[] args) {
        BST<Integer, Integer> bst = new BST<Integer, Integer>(){{
           put(5, 5); put(3, 3); put(8, 8); put(1, 1); put(4, 4); put(6, 6); put(9, 9);
           put(0, 0); put(2, 2); put(7, 7);
        }};
        System.out.println(bst.avgCompares());  // suppose to be 2.9
    }
}
