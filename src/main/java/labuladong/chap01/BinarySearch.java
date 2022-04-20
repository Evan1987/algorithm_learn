package labuladong.chap01;

/**
 * @author : zhaochengming
 * @date : 2022/4/19 23:09
 * @description : 二分搜索
 */
public class BinarySearch {

    // 标准二分搜索
    public static int search(int[] arr, int target) {
        int left = 0;
        int right = arr.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int curr = arr[mid];
            if (curr < target) {
                left = mid + 1;
            } else if (curr > target) {
                right = mid - 1;
            } else {
                return mid;
            }
        }
        return -1;
    }

    // 有重复数字，寻找左侧边界
    public static int searchLeftBound(int[] arr, int target) {
        int left = 0, right = arr.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int curr = arr[mid];
            if (curr < target) {
                left = mid + 1;
            } else if (curr > target) {
                right = mid - 1;
            } else {
                right = mid - 1;
            }
        }

        // left = right + 1才退出循环，因此left 可能会越数组右边界
        if (left >= arr.length || arr[left] != target) {
            return -1;
        }
        return left;
    }

    // 有重复数字，寻找右侧边界
    public static int searchRightBound(int[] arr, int target) {
        int left = 0, right = arr.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int curr = arr[mid];
            if (curr < target) {
                left = mid + 1;
            } else if (curr > target) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        // left = right + 1才退出循环，因此right 可能会越数组左边界
        if (right < 0 || arr[right] != target) {
            return -1;
        }
        return right;
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 5, 5, 5, 6, 7, 8, 9};
        System.out.println(search(arr, 5));
        System.out.println(searchLeftBound(arr, 5));
        System.out.println(searchLeftBound(arr, 10));
        System.out.println(searchRightBound(arr, 5));
    }

}
