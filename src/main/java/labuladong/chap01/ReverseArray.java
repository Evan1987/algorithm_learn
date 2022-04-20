package labuladong.chap01;

/**
 * @author : zhaochengming
 * @date : 2022/4/19 23:05
 * @description : 翻转数组
 */
public class ReverseArray {

    public static void solve(int[] arr) {
        int left = 0, right = arr.length - 1;
        while (left < right) {
            int temp = arr[left];
            arr[left] = arr[right];
            arr[right] = temp;
            left ++;
            right --;
        }
    }

    public static void main(String[] args) {
        int[] arr = new int[]{1, 2, 3, 4, 5, 6, 7};
        solve(arr);
        for (int e: arr) {
            System.out.println(e);
        }
    }
}
