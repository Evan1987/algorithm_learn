package leetcode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author : zhaochengming
 * @date : 2023/7/29 15:16
 * @description : https://leetcode.cn/problems/split-array-into-fibonacci-sequence/
 * 将字符串拆成斐波那契数组
 */
public class L0842_SplitArrayIntoFibonacciSeq {
    static class Solution {

        // 回溯方法
        public List<Integer> splitIntoFibonacci(String num) {
            List<Integer> res = new ArrayList<>();
            if (num.length() < 3) return res;

            if (backTrack(res, num, 0, 0, 0)) {
                return res;
            }
            return new ArrayList<>();
        }

        private boolean backTrack(List<Integer> buffer, String num, int startIndex, int sum, int prev) {
            if (startIndex == num.length()) return buffer.size() >= 3;

            String sumStr = String.valueOf(sum);

            // 剪枝
            int hi;     // 回溯寻找的长度
            if (num.charAt(startIndex) == '0') {
                hi = 1;
            } else if (buffer.isEmpty()) {
                // 第一个元素
                hi = (num.length() - 1) / 2;
            } else if (buffer.size() == 1) {
                // 第二个元素
                hi = (num.length() - startIndex) / 2;
            } else {
                if (num.length() - startIndex < sumStr.length()) return false;
                hi = sumStr.length();
            }

            // 不能超过int范围
            hi = Math.min(10, hi);

            long curLong = 0L;
            for (int i = 0; i < hi; i ++) {
                int index = startIndex + i;
                curLong = curLong * 10 + num.charAt(index) - '0';

                // 溢出控制，curLong本身不能溢出，且如果斐波那契数列其后还要有值时也不能溢出
                if (curLong > Integer.MAX_VALUE || (index < num.length() - 1 && curLong + prev > Integer.MAX_VALUE)) {
                    break;
                }

                if (buffer.size() >= 2) {
                    // 检查是否满足斐波那契
                    if (i < sumStr.length()) {
                       if (num.charAt(index) != sumStr.charAt(i)) break;
                       // 继续检查
                       if (i < sumStr.length() - 1) continue;
                    }
                }

                int cur = (int) curLong;
                buffer.add(cur);

                if (backTrack(buffer, num, index + 1, cur + prev, cur)) {
                    return true;
                }

                buffer.remove(buffer.size() - 1);
            }

            return false;
        }

        // self method
        public List<Integer> splitIntoFibonacci2(String num) {
            int length = num.length();
            if (length < 3) return new ArrayList<>();

            // pick first
            int firstStartIndex = 0;
            int firstLow = 1;
            int firstHigh = num.charAt(firstStartIndex) == '0' ? 1 : Math.min(10, (length - 1) / 2);   // 不能超过int范围
            long first, second;
            for (int firstLength = firstLow; firstLength <= firstHigh; firstLength ++) {
                int secondStartIndex = firstStartIndex + firstLength;
                // pick first
                first = Long.parseLong(num.substring(firstStartIndex, secondStartIndex));
                if (first > Integer.MAX_VALUE) break;

                // pick second
                int secondLow = 1;
                int secondHigh = num.charAt(secondStartIndex) == '0' ? 1 : Math.min(10, (length - firstLength) / 2);
                for (int secondLength = secondLow; secondLength <= secondHigh; secondLength ++) {
                    int secondEndIndex = secondStartIndex + secondLength;
                    second = Long.parseLong(num.substring(secondStartIndex, secondEndIndex));
                    if (second > Integer.MAX_VALUE || second + first > Integer.MAX_VALUE) break;

                    List<Integer> buffer = new ArrayList<>();
                    buffer.add((int) first);
                    buffer.add((int) second);
                    List<Integer> res = findFibonacci(buffer, num, secondEndIndex);
                    if (res != null) return res;
                }
            }

            return new ArrayList<>();
        }

        private List<Integer> findFibonacci(List<Integer> buffer, String num, int startIndex) {
            if (startIndex == num.length()) return buffer;

            int n = buffer.size();
            int next = buffer.get(n - 2) +  buffer.get(n - 1);
            String targetStr = String.valueOf(next);
            int length = targetStr.length();

            int endIndex = startIndex + length;
            if (endIndex > num.length()) return null;
            if (targetStr.equals(num.substring(startIndex, endIndex))) {
                buffer.add(next);
                return findFibonacci(buffer, num, endIndex);
            }

            return null;
        }

    }

    public static void main(String[] args) {
        String num = "417420815174208193484163452262453871040871393665402264706273658371675923077949581449611550452755";
        Solution sol = new Solution();
        sol.splitIntoFibonacci(num);
    }
}
